package com.omar.fbank.customer;

import com.omar.fbank.account.Account;
import com.omar.fbank.account.AccountRepository;
import com.omar.fbank.address.AddressService;
import com.omar.fbank.customer.dto.CustomerDtoMapper;
import com.omar.fbank.customer.dto.CustomerRequestDto;
import com.omar.fbank.customer.dto.CustomerResponseDto;
import com.omar.fbank.customer.exception.CustomerNotFoundException;
import com.omar.fbank.customer.exception.EmailAlreadyExistsException;
import com.omar.fbank.customer.exception.InvalidNifException;
import com.omar.fbank.customeraccount.CustomerAccount;
import com.omar.fbank.customeraccount.CustomerAccountRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerRepository repository;
    private final AddressService addressService;
    private final CustomerDtoMapper customerDtoMapper;
    private final CustomerAccountRepository customerAccountRepository;
    private final AccountRepository accountRepository;

    public Optional<CustomerResponseDto> getCustomerDtoById(UUID customerId) {
        return Optional.ofNullable(customerDtoMapper.toResponseDto(repository.findById(customerId).orElseThrow()));
    }

    public Optional<Customer> getCustomerById(UUID customerId) {
        return repository.findById(customerId);
    }

    public List<CustomerResponseDto> getCustomersDto() {
        return repository.findAll()
                .stream()
                .map(customerDtoMapper::toResponseDto)
                .toList();
    }

    public CustomerResponseDto createCustomer(@Valid CustomerRequestDto customerRequestDto) {
        if (!NifValidator.isValidNIF(customerRequestDto.documentId())) {
            throw new InvalidNifException();
        }

        return customerDtoMapper.toResponseDto(repository.saveAndFlush(customerDtoMapper.toEntity(customerRequestDto)));
    }

    public void updateCustomer(UUID customerId, @Valid CustomerRequestDto customerRequestDto) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        if (!NifValidator.isValidNIF(customerRequestDto.documentId())) {
            throw new InvalidNifException();
        }
        customer.setDocumentId(customerRequestDto.documentId());

        if (repository.existsByEmailAndIdNot(customerRequestDto.email(), customerId)) {
            throw new EmailAlreadyExistsException();
        }
        customer.setEmail(customerRequestDto.email());

        customer.setName(customerRequestDto.name());
        customer.setLastName(customerRequestDto.lastName());
        customer.setPhone(customerRequestDto.phone());

        addressService.updateAddress(customer.getAddress().getId(), customerRequestDto.address());
    }

    public void deleteCustomer(UUID customerId) {
        Customer customer = getCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);

        List<Account> accounts = customerAccountRepository.findByCustomer(customer)
                .stream()
                .map(CustomerAccount::getAccount)
                .toList();

        for (Account account : accounts) {
            CustomerAccount ca = customerAccountRepository.findByCustomerAndAccount(customer, account).orElseThrow();

            if (!ca.isOwner()) {
                customerAccountRepository.delete(ca);
            } else {
                long otherOwners = customerAccountRepository.countOtherOwners(account.getId(), customerId);

                customerAccountRepository.delete(ca);
                if (otherOwners == 0) {
                    customerAccountRepository.deleteByAccounts(accounts);
                    accountRepository.deleteAllAccounts(accounts);
                }
            }


        }

        repository.deleteById(customerId);
    }

    public void reactivateCustomer(UUID customerId) {
        if (repository.getDeletedCustomerById(customerId) == null) {
            throw new CustomerNotFoundException();
        } else {
            addressService.reactivateAddress(repository.getDeletedCustomerById(customerId).getAddress().getId());
            repository.reactivateCustomerById(customerId);
        }
    }
}
