package com.omar.fbank.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer getCustomerById(UUID customerId);

    boolean existsByEmailAndIdNot(String email, UUID id);
}
