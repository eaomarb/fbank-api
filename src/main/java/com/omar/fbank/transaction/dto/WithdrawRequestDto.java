package com.omar.fbank.transaction.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawRequestDto(
        UUID accountId,
        BigDecimal amount,
        String concept
) {
}
