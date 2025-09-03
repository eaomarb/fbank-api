package com.omar.fbank.account;

import com.omar.fbank.customeraccount.CustomerAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @GetMapping("/{id}")
    public Optional<Account> findAccountById(@PathVariable UUID id) {
        return service.findAccountById(id);
    }

    @GetMapping("")
    public List<Account> findAccounts() {
        return service.findAccounts();
    }

    @PostMapping("/accounts/{accountId}/customers")
    public void addCustomer(@PathVariable UUID accountId, @Valid @RequestBody UUID customerId) {
        service.addCustomerToAccount(accountId, customerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable UUID id) {
        service.deleteAccount(id);
    }
}
