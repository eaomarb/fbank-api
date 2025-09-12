package com.omar.fbank.customeraccount;

import com.omar.fbank.account.Account;
import com.omar.fbank.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, UUID> {
    Page<CustomerAccount> findByAccount(Account account, Pageable pageable);

    Optional<CustomerAccount> findByCustomerAndAccount(Customer customer, Account account);

    @Query("SELECT COUNT(ca) FROM CustomerAccount ca WHERE ca.account.id = :account_id AND ca.isOwner = true")
    long countByAccountAndIsOwner(@Param("account_id") UUID account_id);

    @Query("SELECT COUNT(ca) FROM CustomerAccount ca WHERE ca.account.id = :account_id AND ca.isOwner = true AND ca.customer.id != :customer_id")
    long countOtherOwners(@Param("account_id") UUID account_id, @Param("customer_id") UUID customer_id);

    long countCustomerAccountByAccount(Account account);

    Page<CustomerAccount> findByCustomer_Id(UUID customerId, Pageable pageable);

    List<CustomerAccount> findByCustomer(Customer customer);

    void deleteByAccount(Account account);
}
