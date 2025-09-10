package com.omar.fbank.customer;

import com.omar.fbank.account.AccountService;
import com.omar.fbank.account.dto.AccountResponseDto;
import com.omar.fbank.customer.dto.CustomerRequestDto;
import com.omar.fbank.customer.dto.CustomerResponseDto;
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
    public Optional<CustomerResponseDto> getCustomerById(@PathVariable UUID customerId) {
        return service.getCustomerDtoById(customerId);
    }

    @GetMapping("")
    public List<CustomerResponseDto> getCustomers() {
        return service.getCustomersDto();
    }

    @GetMapping("{customerId}/transactions")
    public List<TransactionResponseDto> getTransactionsByCustomerId(@PathVariable UUID customerId) {
        return transactionService.getTransactionsDtoByCustomerId(customerId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto createCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        return service.createCustomer(customerRequestDto);
    }

    @PostMapping("/{customerId}/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDto createAccount(@Valid @PathVariable UUID customerId) {
        return accountService.createAccount(customerId);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable UUID customerId, @Valid @RequestBody CustomerRequestDto customerRequestDto) {
        service.updateCustomer(customerId, customerRequestDto);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable UUID customerId) {
        service.deleteCustomer(customerId);
    }

    @PostMapping("/{customerId}/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivateCustomer(@PathVariable UUID customerId) {
        service.reactivateCustomer(customerId);
    }

}