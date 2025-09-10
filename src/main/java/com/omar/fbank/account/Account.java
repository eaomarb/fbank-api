package com.omar.fbank.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE accounts SET status = 'INACTIVE', deleted_at = now() WHERE id = ?")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "iban", nullable = false, unique = true)
    private String iban;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    Instant updatedAt;

    @Column(name = "deleted_at", insertable = false)
    private Instant deletedAt;
}
