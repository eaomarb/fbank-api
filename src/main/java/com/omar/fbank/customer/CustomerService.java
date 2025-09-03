package com.omar.fbank.customer;

import com.omar.fbank.address.AddressService;
import com.omar.fbank.customer.exception.CustomerNotFoundException;
import com.omar.fbank.customer.exception.EmailAlreadyExistsException;
import com.omar.fbank.customer.exception.InvalidNifException;
import jakarta.transaction.Transactional;
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

    public Optional<Customer> findById(UUID id) {
        return repository.findById(id);
    }

    public List<Customer> findCustomers() {
        return repository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        if (!NifValidator.isValidNIF(customer.getDocumentId())) {
            throw new InvalidNifException();
        }

        return repository.save(customer);
    }

    public void updateCustomer(UUID id, Customer customer) {
        Customer existingCustomer = repository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        // Update Customer
        if (NifValidator.isValidNIF(customer.getDocumentId())) {
            existingCustomer.setDocumentId(customer.getDocumentId());
        } else {
            throw new InvalidNifException();
        }

        if (repository.existsByEmail(customer.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        existingCustomer.setName(customer.getName());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setLastName(customer.getLastName());

        addressService.updateAddress(existingCustomer.getAddress().getId(), existingCustomer.getAddress());
    }

    public void deleteCustomer(UUID id) {
        if (!repository.existsById(id)) {
            throw new CustomerNotFoundException();
        }
        repository.deleteById(id);
    }
}
