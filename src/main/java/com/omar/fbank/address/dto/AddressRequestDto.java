package com.omar.fbank.address.dto;

import jakarta.validation.constraints.*;

public record AddressRequestDto(
        @NotBlank(message = "Street name can't be blank.")
        String streetName,

        @NotBlank(message = "Street number can't be blank.")
        String streetNumber,

        String floor,
        String door,

        @NotBlank(message = "Postal code can't be blank.")
        @Size(min = 5, max = 5, message = "Postal code must contain 5 digits.")
        @Positive(message = "Postal code must be a positive number.")
        @Min(value = 1000, message = "Postal code is invalid.")
        @Max(value = 52999, message = "Postal code is invalid.")
        String postalCode,

        @NotBlank(message = "City can't be blank.")
        String city,

        @NotBlank(message = "Province can't be blank.")
        String province

) {
}
