package com.omar.fbank.account.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        BigDecimal balance,
        String iban
) {
}
