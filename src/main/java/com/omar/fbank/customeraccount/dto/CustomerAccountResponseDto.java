package com.omar.fbank.customeraccount.dto;

import java.util.UUID;

public record CustomerAccountResponseDto(
        UUID id,
        UUID customerId,
        UUID accountId,
        boolean isOwner
) {
}
