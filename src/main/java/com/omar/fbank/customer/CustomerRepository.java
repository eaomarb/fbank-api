package com.omar.fbank.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer getCustomerById(UUID customerId);

    boolean existsByEmailAndIdNot(String email, UUID id);

    @Query(value = "SELECT * FROM customers c WHERE c.id = :customer_id", nativeQuery = true)
    Customer getDeletedCustomerById(UUID customer_id);

    @Modifying
    @Query(value = "UPDATE customers SET deleted = false WHERE id = :customer_id", nativeQuery = true)
    void reactivateCustomerById(UUID customer_id);
}
