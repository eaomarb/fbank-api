package com.omar.fbank.transaction;

import com.omar.fbank.transaction.dto.TransactionResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<TransactionResponseDto> findByAccountIdOrderByCreatedAt(UUID accountId);

    @Query("SELECT t FROM Transaction t WHERE t.account.id IN (:accountIds)")
    List<Transaction> findByAccountIdOrderByCreatedAtAsc(Collection<UUID> accountIds);
}
