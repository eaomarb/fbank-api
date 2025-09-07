package com.omar.fbank.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDto(
        @NotNull
        UUID accountId,

        @DecimalMin(value = "0", inclusive = false, message = "Transaction amount must be positive.")
        @NotNull
        BigDecimal amount,

        String beneficiaryName,
        String concept,

        @NotBlank
        String beneficiaryIban) {
}

