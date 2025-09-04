package com.omar.fbank.customeraccount;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customersAccounts")
@RequiredArgsConstructor
public class CustomerAccountController {
    private final CustomerAccountService service;

    @GetMapping("")
    public List<CustomerAccount> getCustomerAccounts() {
        return service.getCustomerAccounts();
    }

    @GetMapping("/{customerAccountId}")
    public Optional<CustomerAccount> getCustomerAccountById(@PathVariable UUID customerAccountId) {
        return service.getCustomerAccountById(customerAccountId);
    }

    @GetMapping("/customer/{customerId}")
    public List<CustomerAccount> getCustomerAccountsByCustomerId(@PathVariable UUID customerId) {
        return service.getCustomerAccountsByCustomerId(customerId);
    }

    @GetMapping("/account/{accountId}")
    public List<CustomerAccount> getCustomerAccountByAccountId(@PathVariable UUID accountId) {
        return service.getCustomerAccountsByAccountId(accountId);
    }

    @GetMapping("/{customerId}/{accountId}")
    public Optional<CustomerAccount> getCustomerAccountByCustomerAndAccountId(@PathVariable UUID customerId, @PathVariable UUID accountId) {
        return service.getCustomerAccountByCustomerAndAccountIds(customerId, accountId);
    }

    @PostMapping("/{accountId}/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createCustomerAccount(@Valid @PathVariable UUID accountId, @Valid @PathVariable UUID customerId) {
        service.addCustomerToAccount(accountId, customerId);
    }

    @PatchMapping("/{customerAccountId}/ownership")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomerAccountOwnership(@Valid @PathVariable UUID customerAccountId, @Valid @RequestParam boolean isOwner) {
        service.updateCustomerAccountOwnership(customerAccountId, isOwner);
    }

    @DeleteMapping("/{customerAccountId}")
    public void deleteCustomerAccountById(@PathVariable UUID customerAccountId) {
        service.deleteCustomerAccountById(customerAccountId);
    }
}
