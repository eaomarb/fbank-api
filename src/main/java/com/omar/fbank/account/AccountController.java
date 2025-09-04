package com.omar.fbank.account;

import com.omar.fbank.transaction.TransactionService;
import com.omar.fbank.transaction.dto.TransactionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;
    private final TransactionService transactionService;

    @GetMapping("/{accountId}")
    public Optional<Account> getAccountById(@PathVariable UUID accountId) {
        return service.getAccountById(accountId);
    }

    @GetMapping("")
    public List<Account> getAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/{accountId}/transactions")
    public List<TransactionResponseDto> getTransactionsByAccountId(@PathVariable UUID accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable UUID accountId) {
        service.deleteAccount(accountId);
    }
}
