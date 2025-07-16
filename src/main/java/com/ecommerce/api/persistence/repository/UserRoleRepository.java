package com.ecommerce.api.persistence.repository;

import com.ecommerce.api.model.enums.RoleName;
import com.ecommerce.api.persistence.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRoleName(RoleName roleName);
}