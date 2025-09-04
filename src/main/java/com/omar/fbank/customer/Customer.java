package com.omar.fbank.customer;

import com.omar.fbank.address.Address;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", nullable = false)
        private UUID id;

        @Column(name = "name", nullable = false)
        @NotBlank(message = "Name can't be empty.")
        private String name;

        @Column(name = "last_name", nullable = false)
        @NotBlank(message = "Last name can't be empty.")
        private String lastName;

        @Column(name = "document_id", unique = true, nullable = false)
        @NotBlank(message = "Document ID can't be empty.")
        private String documentId;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "address", referencedColumnName = "id", nullable = false)
        @Valid
        private Address address;

        @Column(name = "age", nullable = false)
        @Min(value = 18, message = "Age must be 18 or greater")
        private Integer age;

        @Column(name = "email", nullable = false, unique = true)
        @NotBlank(message = "Email can't be empty.")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email")
        private String email;

        @Column(name = "phone", nullable = false, length = 9)
        @Size(min = 9, max = 9, message = "Phone number must contain 9 digits.")
        @NotBlank(message = "Phone number can't be empty.")
        @Positive(message = "Phone number must be a positive number.")
        @Min(value = 600000000, message = "Phone number is invalid.")
        @Max(value = 999999999, message = "Phone number is invalid.")
        private String phone;
}
