package com.omar.fbank.customer;

import com.omar.fbank.address.AddressService;
import com.omar.fbank.customer.dto.CustomerDtoMapper;
import com.omar.fbank.customer.dto.CustomerRequestDto;
import com.omar.fbank.customer.dto.CustomerResponseDto;
import com.omar.fbank.customer.exception.CustomerNotFoundException;
import com.omar.fbank.customer.exception.EmailAlreadyExistsException;
import com.omar.fbank.customer.exception.InvalidNifException;
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

    public Optional<CustomerResponseDto> getCustomerDtoById(UUID customerId) {
        return Optional.ofNullable(customerDtoMapper.toResponseDto(repository.findById(customerId).orElseThrow()));
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

        return customerDtoMapper.toResponseDto(repository.save(customerDtoMapper.toEntity(customerRequestDto)));
    }

    public void updateCustomer(UUID customerId, @Valid CustomerRequestDto customerRequestDto) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        if (!NifValidator.isValidNIF(customerRequestDto.documentId())) {
            throw new InvalidNifException();
        }
        customer.setDocumentId(customerRequestDto.documentId());

        if (repository.existsByEmailAndIdNot(customerRequestDto.email(), customerId)){
            throw new EmailAlreadyExistsException();
        }
        customer.setEmail(customerRequestDto.email());

        customer.setName(customerRequestDto.name());
        customer.setLastName(customerRequestDto.lastName());
        customer.setPhone(customerRequestDto.phone());

        addressService.updateAddress(customer.getAddress().getId(), customerRequestDto.addressRequestDto());
    }

    public void deleteCustomer(UUID customerId) {
        if (!repository.existsById(customerId)) {
            throw new CustomerNotFoundException();
        }
        repository.deleteById(customerId);
    }
}
