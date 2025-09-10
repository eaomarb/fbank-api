package com.omar.fbank.customer.dto;

import com.omar.fbank.address.dto.AddressRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CustomerRequestDto(
        @NotBlank(message = "Name can't be empty.")
        String name,

        @NotBlank(message = "Last name can't be empty.")
        String lastName,

        @NotBlank(message = "Document ID can't be empty.")
        String documentId,

        @Valid
        AddressRequestDto address,

        @NotNull
        @Min(value = 18, message = "Age must be 18 or greater")
        Integer age,

        @NotBlank(message = "Email can't be empty.")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email")
        String email,

        @Size(min = 9, max = 9, message = "Phone number must contain 9 digits.")
        @NotBlank(message = "Phone number can't be empty.")
        @Positive(message = "Phone number must be a positive number.")
        @Min(value = 600000000, message = "Phone number is invalid.")
        @Max(value = 999999999, message = "Phone number is invalid.")
        String phone
) {
}
