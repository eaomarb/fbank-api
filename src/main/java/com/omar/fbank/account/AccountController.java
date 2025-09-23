package com.omar.fbank.account;

import com.omar.fbank.account.dto.AccountResponseDto;
import com.omar.fbank.transaction.TransactionService;
import com.omar.fbank.transaction.dto.TransactionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Accounts", description = "Manage bank accounts and view their transactions")
public class AccountController {
    private final AccountService service;
    private final TransactionService transactionService;

    @GetMapping("/{accountId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAccount(#accountId)")
    @Operation(
            summary = "Get account by ID",
            description = "Returns account details if the user has access or is an admin"
    )
    public Optional<AccountResponseDto> getAccountById(@Parameter(description = "UUID of the account") @PathVariable UUID accountId) {
        return service.getAccountDtoById(accountId);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Get all accounts (admin only)"
    )
    public Page<AccountResponseDto> getAccounts(Pageable pageable) {
        return service.getAccountsDto(pageable);
    }

    @GetMapping("/{accountId}/transactions")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAccount(#accountId)")
    @Operation(
            summary = "Get transactions for account",
            description = "Returns transactions for a specific account"
    )
    public Page<TransactionResponseDto> getTransactionsByAccountId(@Parameter(description = "UUID of the account") @PathVariable UUID accountId,
                                                                   @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return transactionService.getTransactionsDtoByAccountId(accountId, pageable);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canOperateOnAccount(#accountId)")
    @Operation(
            summary = "Delete account",
            description = "Deletes an account if the user has permission or is an admin"
    )
    public void deleteAccount(@Parameter(description = "UUID of the account") @PathVariable UUID accountId) {
        service.deleteAccount(accountId);
    }

}
