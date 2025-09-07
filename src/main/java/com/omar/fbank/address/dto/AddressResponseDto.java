package com.omar.fbank.address.dto;

import java.util.UUID;

public record AddressResponseDto(
        UUID id,
        String streetName,
        String streetNumber,
        String floor,
        String door,
        String postalCode,
        String city,
        String province
) {
}