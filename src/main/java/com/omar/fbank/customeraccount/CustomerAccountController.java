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
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<CustomerAccount> getCustomerAccount(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping("/customer/{id}")
    public List<CustomerAccount> getCustomerAccountByCustomerId(@PathVariable UUID id) {
        return service.findByCustomerId(id);
    }

    @GetMapping("/account/{id}")
    public List<CustomerAccount> getCustomerAccountByAccountId(@PathVariable UUID id) {
        return service.findByAccountId(id);
    }

    @GetMapping("/{customerId}/{accountId}")
    public Optional<CustomerAccount> getCustomerAccountByCustomerIdAndAccountId(@PathVariable UUID customerId, @PathVariable UUID accountId) {
        return service.findByCustomerAndAccount(customerId, accountId);
    }

    @PutMapping("/{accountId}/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCustomerToAccount(@Valid @PathVariable UUID accountId, @Valid @PathVariable UUID customerId) {
        service.addCustomerToAccount(accountId, customerId);
    }

    @PatchMapping("/{id}/ownership")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomerAccount(@Valid @PathVariable UUID id, @Valid @RequestParam boolean isOwner) {
        service.update(id, isOwner);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerAccount(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
