package com.ecommerce.api.persistence.repository;

import com.ecommerce.api.persistence.entity.User;
import com.ecommerce.api.persistence.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);

    Optional<User> findByEmail(String email);
}
