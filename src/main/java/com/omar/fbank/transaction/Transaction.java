package com.omar.fbank.transaction;

import com.omar.fbank.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    UUID id;

    @Column(name = "transaction_date", nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "origin_account", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    Account account;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    @DecimalMin(value = "0", inclusive = false, message = "Transaction amount must be positive.")
    BigDecimal amount;

    @Column(name = "beneficiary_name")
    String beneficiaryName;

    @Column(name = "concept")
    String concept;

    @Column(name = "beneficiary_iban")
    String beneficiaryIban;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    TransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    TransactionType transactionType;
}
