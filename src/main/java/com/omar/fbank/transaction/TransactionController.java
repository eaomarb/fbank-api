package com.omar.fbank.transaction;

import com.omar.fbank.transaction.dto.DepositRequestDto;
import com.omar.fbank.transaction.dto.TransactionResponseDto;
import com.omar.fbank.transaction.dto.TransferRequestDto;
import com.omar.fbank.transaction.dto.WithdrawRequestDto;
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
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<TransactionResponseDto> getTransactions(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return transactionService.getTransactionsDto(pageable);
    }

    @GetMapping("/{transactionId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessTransaction(#transactionId)")
    public TransactionResponseDto getTransactionById(@PathVariable UUID transactionId) {
        return transactionService.getTransactionDtoById(transactionId);
    }

    @PostMapping("/deposit")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAccount(#depositRequestDto.accountId)")
    public TransactionResponseDto deposit(@Valid @RequestBody DepositRequestDto depositRequestDto) {
        return transactionService.deposit(depositRequestDto);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAccount(#withdrawRequestDto.accountId)")
    public TransactionResponseDto withdraw(@Valid @RequestBody WithdrawRequestDto withdrawRequestDto) {
        return transactionService.withdraw(withdrawRequestDto);
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAccount(#transferRequestDto.accountId())")
    public TransactionResponseDto transfer(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        return transactionService.transfer(transferRequestDto);
    }

}
