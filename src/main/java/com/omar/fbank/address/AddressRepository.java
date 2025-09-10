package com.omar.fbank.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Modifying
    @Query(value = "UPDATE addresses SET deleted = false WHERE id = :address_id", nativeQuery = true)
    void reactivateCustomerById(UUID address_id);
}
