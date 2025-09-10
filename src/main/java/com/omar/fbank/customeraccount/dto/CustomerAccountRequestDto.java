package com.omar.fbank.customeraccount.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CustomerAccountRequestDto(
        @NotNull
        UUID customerId,
        @NotNull
        UUID accountId,
        @NotNull
        boolean isOwner
) {
}
