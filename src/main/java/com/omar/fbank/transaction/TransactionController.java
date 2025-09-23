package com.omar.fbank.transaction;

import com.omar.fbank.transaction.dto.DepositRequestDto;
import com.omar.fbank.transaction.dto.TransactionResponseDto;
import com.omar.fbank.transaction.dto.TransferRequestDto;
import com.omar.fbank.transaction.dto.WithdrawRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction", description = "Handle deposits, withdrawals, transfers, and view transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List all transactions", description = "Returns all transactions. Only admin can access.")
    public Page<TransactionResponseDto> getTransactions(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return transactionService.getTransactionsDto(pageable);
    }

    @GetMapping("/{transactionId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessTransaction(#transactionId)")
    @Operation(summary = "Get a transaction by ID", description = "Returns the details of a specific transaction if permitted or admin.")
    public TransactionResponseDto getTransactionById(@Parameter(description = "UUID of the transaction") @PathVariable UUID transactionId) {
        return transactionService.getTransactionDtoById(transactionId);
    }

    @PostMapping("/deposit")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAccount(#depositRequestDto.accountId)")
    @Operation(summary = "Deposit into an account", description = "Creates a deposit transaction for the specified account if permitted or admin.")
    public TransactionResponseDto deposit(@Valid @RequestBody DepositRequestDto depositRequestDto) {
        return transactionService.deposit(depositRequestDto);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAccount(#withdrawRequestDto.accountId)")
    @Operation(summary = "Withdraw from an account", description = "Creates a withdrawal transaction from the specified account if permitted or admin.")
    public TransactionResponseDto withdraw(@Valid @RequestBody WithdrawRequestDto withdrawRequestDto) {
        return transactionService.withdraw(withdrawRequestDto);
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAccount(#transferRequestDto.accountId())")
    @Operation(summary = "Transfer between accounts", description = "Creates a transfer transaction from one account to another if permitted or admin.")
    public TransactionResponseDto transfer(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        return transactionService.transfer(transferRequestDto);
    }

}
