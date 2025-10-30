package com.omar.fbank.customer;

import com.omar.fbank.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer getCustomerById(UUID customerId);

    @Query(value = "SELECT * FROM customers c WHERE c.id = :customer_id", nativeQuery = true)
    Customer getDeletedCustomerById(UUID customer_id);

    @Modifying
    @Query(value = "UPDATE customers SET deleted = false WHERE id = :customer_id", nativeQuery = true)
    void reactivateCustomerById(UUID customer_id);

    boolean existsByIdAndUserId(UUID id, UUID userId);

    boolean existsByAddress_IdAndUser_Id(UUID addressId, UUID userId);

    Optional<Customer> findByUser_Id(UUID userId);

    UUID user(User user);
}
