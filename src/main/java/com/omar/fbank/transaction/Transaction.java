package com.omar.fbank.transaction;

import com.omar.fbank.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "origin_account", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Account account;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    @DecimalMin(value = "0", inclusive = false, message = "Transaction amount must be positive.")
    private BigDecimal amount;

    @Column(name = "beneficiary_name")
    private String beneficiaryName;

    @Column(name = "concept")
    private String concept;

    @Column(name = "beneficiary_iban")
    private String beneficiaryIban;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType transactionType;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    Instant updatedAt;
}
