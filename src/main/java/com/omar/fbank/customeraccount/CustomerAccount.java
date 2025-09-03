package com.omar.fbank.customeraccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omar.fbank.account.Account;
import com.omar.fbank.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name = "customer_accounts")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccount {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", nullable = false)
        private UUID id;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false, updatable = false)
        @Valid
        private Customer customer;

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false, updatable = false)
        @Valid
        private Account account;

        @Column(name = "is_owner", nullable = false)
        private boolean isOwner;
}
