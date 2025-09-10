package com.omar.fbank.transaction.dto;

import com.omar.fbank.transaction.TransactionStatus;
import com.omar.fbank.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDto(
        UUID id,
        LocalDateTime createdAt,
        UUID accountId,
        BigDecimal amount,
        String beneficiaryName,
        String concept,
        String beneficiaryIban,
        TransactionStatus transactionStatus,
        TransactionType transactionType) {
}