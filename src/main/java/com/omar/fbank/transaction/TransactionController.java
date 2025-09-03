package com.omar.fbank.transaction;

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
    public List<Transaction> getAllTransactions(){
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable UUID id){
        return transactionService.findById(id);
    }

    @GetMapping("/{accountId}")
    public List<Transaction> getTransactionsByAccountId(@PathVariable UUID accountId){
        return transactionService.findByAccountId(accountId);
    }

    @PostMapping("")
    public Transaction createTransaction(@RequestBody Transaction transaction){
        if (transaction.getTransactionType() == TransactionType.DEPOSIT) {

        }


        return transactionService.save(transaction);
    }
}
