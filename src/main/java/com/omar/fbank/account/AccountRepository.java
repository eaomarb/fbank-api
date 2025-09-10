package com.omar.fbank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByIban(String iban);

    Account findAccountByIban(String iban);

    @Modifying
    @Query(value = "UPDATE customer_accounts SET deleted = true WHERE account_id = :account_id", nativeQuery = true)
    void deleteCustomerAccountByAccountId(UUID account_id);

    @Modifying
    @Query(value = "UPDATE customer_accounts SET deleted_at = now() WHERE account_id = :account_id", nativeQuery = true)
    void updateCustomerAccountDeletedDate(UUID account_id);

    @Modifying
    @Query("DELETE FROM Account WHERE Account IN :accounts")
    void deleteAllAccounts(@Param("accounts") List<Account> accounts);
}
