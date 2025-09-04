package com.omar.fbank.transaction;

import com.omar.fbank.transaction.dto.DepositRequestDto;
import com.omar.fbank.transaction.dto.TransferRequestDto;
import com.omar.fbank.transaction.dto.TransactionResponseDto;
import com.omar.fbank.transaction.dto.WithdrawRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("")
    public List<TransactionResponseDto> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{transactionId}")
    public TransactionResponseDto getTransactionById(@PathVariable UUID transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @PostMapping("/deposit")
    public TransactionResponseDto deposit(@RequestBody DepositRequestDto depositRequestDto) {
        return transactionService.deposit(depositRequestDto);
    }

    @PostMapping("/withdraw")
    public TransactionResponseDto withdraw(@RequestBody WithdrawRequestDto withdrawRequestDto) {
        return transactionService.withdraw(withdrawRequestDto);
    }

    @PostMapping("/transfer")
    public TransactionResponseDto transfer(@RequestBody TransferRequestDto transferRequestDto) {
        return transactionService.transfer(transferRequestDto);
    }
}
