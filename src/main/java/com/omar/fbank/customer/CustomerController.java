package com.omar.fbank.customer;

import com.omar.fbank.account.Account;
import com.omar.fbank.account.AccountService;
import com.omar.fbank.transaction.TransactionService;
import com.omar.fbank.transaction.dto.TransactionResponseDto;
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
    private final TransactionService transactionService;

    @GetMapping("/{customerId}")
    public Optional<Customer> getCustomerById(@PathVariable UUID customerId) {
        return service.getCustomerById(customerId);
    }

    @GetMapping("")
    public List<Customer> getCustomers() {
        return service.getCustomers();
    }

    @GetMapping("{customerId}/transactions")
    public List<TransactionResponseDto> getTransactionsByCustomerId(@PathVariable UUID customerId) {
        return transactionService.getTransactionsByCustomerId(customerId);
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

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable UUID customerId, @Valid @RequestBody Customer customer) {
        service.updateCustomer(customerId, customer);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable UUID customerId) {
        service.deleteCustomer(customerId);
    }

}
