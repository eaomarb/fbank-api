package com.omar.fbank.customer.dto;

import com.omar.fbank.address.dto.AddressResponseDto;

import java.util.UUID;

public record CustomerResponseDto(
        UUID id,
        String name,
        String lastName,
        String documentId,
        AddressResponseDto address,
        Integer age,
        String email,
        String phone
) {
}
