package com.omar.fbank.transaction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public List<Transaction> findByAccountId(UUID accountId) {
        return repository.findByAccountId(accountId);
    }

    public Transaction findById(UUID transactionId) {
        return repository.findById(transactionId).orElseThrow(TransactionNotFoundException::new);
    }
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }
}
