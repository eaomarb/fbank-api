package com.omar.fbank.customer.dto;

import com.omar.fbank.address.dto.AddressResponseDto;
import com.omar.fbank.user.dto.UserResponseDto;

import java.util.UUID;

public record CustomerResponseDto(
        UUID id,
        String name,
        String lastName,
        String documentId,
        AddressResponseDto address,
        Integer age,
        String phone,
        UserResponseDto user
) {
}
