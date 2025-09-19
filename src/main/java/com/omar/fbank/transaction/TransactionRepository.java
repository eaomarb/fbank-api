package com.omar.fbank.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findByAccountId(UUID accountId, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.account.id IN (:accountIds)")
    Page<Transaction> findByAccountIdList(Collection<UUID> accountIds, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.id = :transactionId AND t.account.id IN :accountIds")
    Optional<Transaction> findByIdAndAccountIds(@Param("transactionId") UUID transactionId,
                                                @Param("accountIds") Collection<UUID> accountIds);
}
