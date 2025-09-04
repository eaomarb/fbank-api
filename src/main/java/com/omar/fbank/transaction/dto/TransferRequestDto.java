package com.omar.fbank.transaction.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDto(
        UUID accountId,
        BigDecimal amount,
        String beneficiaryName,
        String concept,
        String beneficiaryIban) {
}

