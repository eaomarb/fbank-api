package com.omar.fbank.customer;

import com.omar.fbank.account.Account;
import com.omar.fbank.account.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;
    private final AccountService accountService;

    @GetMapping("/{id}")
    public Optional<Customer> findCustomerById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping("")
    public List<Customer> findCustomers() {
        return service.findCustomers();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return service.createCustomer(customer);
    }

    @PostMapping("/{customerId}/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@Valid @PathVariable UUID customerId) {
        return accountService.createAccount(customerId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable UUID id, @Valid @RequestBody Customer customer) {
        service.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable UUID id) {
        service.deleteCustomer(id);
    }

}
