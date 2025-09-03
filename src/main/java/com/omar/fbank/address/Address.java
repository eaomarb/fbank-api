package com.omar.fbank.address;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", nullable = false)
        private UUID id;

        @Column(name = "street_name", nullable = false)
        @NotBlank(message = "Street name cannot be blank.")
        private String streetName;

        @Column(name = "street_number", nullable = false)
        @NotBlank(message = "Street number can't be blank.")
        private String streetNumber;

        @Column(name = "floor")
        private String floor;

        @Column(name = "door")
        private String door;

        @Column(name = "postal_code", nullable = false)
        @NotBlank(message = "Postal code can't be blank.")
        @Size(min = 5, max = 5, message = "Postal code must contain 5 digits.")
        @Positive(message = "Postal code must be a positive number.")
        @Min(value = 1000, message = "Postal code is invalid.")
        @Max(value = 52999, message = "Postal code is invalid.")
        private String postalCode;

        @Column(name = "city", nullable = false)
        @NotBlank(message = "City can't be blank.")
        private String city;

        @Column(name = "province", nullable = false)
        @NotBlank(message = "Province can't be blank.")
        private String province;
}