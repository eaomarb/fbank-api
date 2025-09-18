package com.omar.fbank.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE addresses SET deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted = false")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "street_name", nullable = false)
    @NotBlank(message = "Street name can't be blank.")
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

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    Instant updatedAt;

    @Column(name = "deleted_at", insertable = false)
    Instant deletedAt;
}