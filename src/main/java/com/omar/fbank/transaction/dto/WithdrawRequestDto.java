package com.omar.fbank.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawRequestDto(
        @NotNull
        UUID accountId,

        @NotNull
        @DecimalMin(value = "0", inclusive = false, message = "Transaction amount must be positive.")
        BigDecimal amount,

        String concept
) {
}
