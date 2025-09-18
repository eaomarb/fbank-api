package com.omar.fbank.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmailAndIdNot(String email, UUID id);

    Optional<User> findByEmail(String email);

}
