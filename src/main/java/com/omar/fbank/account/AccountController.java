package com.omar.fbank.account;

import com.omar.fbank.account.dto.AccountResponseDto;
import com.omar.fbank.transaction.TransactionService;
import com.omar.fbank.transaction.dto.TransactionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;
    private final TransactionService transactionService;

    @GetMapping("/{accountId}")
    @PreAuthorize("hasRole('ADMIN') or @authService.canAccessAccount(#accountId)")
    public Optional<AccountResponseDto> getAccountById(@PathVariable UUID accountId) {
        return service.getAccountDtoById(accountId);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AccountResponseDto> getAccounts(Pageable pageable) {
        return service.getAccountsDto(pageable);
    }

    @GetMapping("/{accountId}/transactions")
    @PreAuthorize("hasRole('ADMIN') or @authService.canAccessAccount(#accountId)")
    public Page<TransactionResponseDto> getTransactionsByAccountId(@PathVariable UUID accountId,
                                                                   @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return transactionService.getTransactionsDtoByAccountId(accountId, pageable);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or @authService.canAccessAccount(#accountId)")
    public void deleteAccount(@PathVariable UUID accountId) {
        service.deleteAccount(accountId);
    }

}
