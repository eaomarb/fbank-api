package com.omar.fbank.account.dto;

import com.omar.fbank.account.AccountStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        BigDecimal balance,
        String iban,
        AccountStatus status
) {
}
