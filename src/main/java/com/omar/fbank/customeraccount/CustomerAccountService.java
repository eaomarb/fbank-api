package com.omar.fbank.customeraccount;

import com.omar.fbank.account.Account;
import com.omar.fbank.account.AccountRepository;
import com.omar.fbank.account.AccountStatus;
import com.omar.fbank.account.exception.AccountNotFoundException;
import com.omar.fbank.account.exception.NotEmptyAccountException;
import com.omar.fbank.customer.Customer;
import com.omar.fbank.customer.CustomerRepository;
import com.omar.fbank.customer.exception.CustomerNotFoundException;
import com.omar.fbank.customeraccount.dto.CustomerAccountDtoMapper;
import com.omar.fbank.customeraccount.dto.CustomerAccountRequestDto;
import com.omar.fbank.customeraccount.dto.CustomerAccountResponseDto;
import com.omar.fbank.customeraccount.exception.*;
import com.omar.fbank.transaction.exception.InactiveAccountException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerAccountService {
    private final CustomerAccountRepository repository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CustomerAccountDtoMapper mapper;

    public List<CustomerAccountResponseDto> getCustomerAccounts() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public Optional<CustomerAccountResponseDto> getCustomerAccountDtoById(UUID customerAccountId) {
        return Optional.of(mapper.toResponseDto(repository.findById(customerAccountId).orElseThrow(CustomerNotFoundException::new)));
    }

    public Optional<CustomerAccount> getCustomerAccountById(UUID customerAccountId) {
        return Optional.of(repository.findById(customerAccountId).orElseThrow(CustomerNotFoundException::new));
    }

    public List<CustomerAccountResponseDto> getCustomerAccountsByCustomerId(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        return repository.findByCustomer(customer)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public List<CustomerAccountResponseDto> getCustomerAccountsDtoByAccountId(UUID accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return repository.findByAccount(account)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public List<CustomerAccount> getCustomerAccountsByAccountId(UUID accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return repository.findByAccount(account);
    }

    public Optional<CustomerAccountResponseDto> getCustomerAccountByCustomerAndAccountIds(UUID customerId, UUID accountId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        return Optional.ofNullable(mapper.toResponseDto(repository.findByCustomerAndAccount(customer, account).orElseThrow(CustomerAccountNotFoundByCustomerAndAccountException::new)));
    }

    public void create(Customer customer, Account account) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customer);
        Optional<Account> optionalAccount = Optional.ofNullable(account);

        if (optionalAccount.isPresent() && optionalAccount.orElseThrow().getStatus() == AccountStatus.INACTIVE) {
            throw new InactiveAccountException();
        }

        if (optionalCustomer.isPresent() && optionalAccount.isPresent()) {
            List<CustomerAccount> customerAccountList = getCustomerAccountsByAccountId(account.getId());

            for (CustomerAccount customerAccount : customerAccountList) {
                if (customerAccount.isOwner()) {
                    repository.save(mapper.toEntity(new CustomerAccountRequestDto(customer.getId(), account.getId(), false)));
                    return;
                }
            }

            repository.save(mapper.toEntity(new CustomerAccountRequestDto(customer.getId(), account.getId(), true)));
        }
    }

    public void addCustomerToAccount(UUID accountId, UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        Optional<CustomerAccount> customerAccount = repository.findByCustomerAndAccount(customer, account);

        if (customerAccount.isPresent()) {
            throw new SelfAssignmentNotAllowedException();
        }

        if (customer != null && account.getStatus() == AccountStatus.ACTIVE) {
            create(customer, account);
        }
    }


    public void updateCustomerAccountOwnership(UUID customerAccountId, boolean isOwner) {
        CustomerAccount customerAccount = getCustomerAccountById(customerAccountId)
                .orElseThrow(CustomerAccountNotFoundException::new);

        if (isOwner == customerAccount.isOwner()) {
            throw new AlreadyCurrentValueException(isOwner);
        }

        if (customerAccount.isOwner()) {
            long ownerCount = repository.countByAccountAndIsOwner(customerAccount.getAccount().getId());

            if (ownerCount == 1) {
                throw new OnlyOneOwnerCustomerAccountException();
            }
        }

        customerAccount.setOwner(isOwner);
        repository.save(customerAccount);
    }

    public void deleteCustomerAccountById(UUID customerAccountId) {
        CustomerAccount customerAccount = getCustomerAccountById(customerAccountId)
                .orElseThrow(CustomerAccountNotFoundException::new);

        if (customerAccount.getAccount().getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new NotEmptyAccountException();
        }

        long ownerCount = repository.countByAccountAndIsOwner(customerAccount.getAccount().getId());
        long customerCount = repository.countCustomerAccountByAccount(customerAccount.getAccount());

        if (customerAccount.isOwner() && ownerCount == 1 && customerCount > 1) {
            throw new OnlyOneOwnerCustomerAccountException();
        }

        repository.deleteById(customerAccountId);
    }
}
