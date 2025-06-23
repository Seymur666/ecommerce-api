package com.ecommerce.api.persistence.entity;

import com.ecommerce.api.model.enums.RoleName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_role")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 50)
    RoleName roleName;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToMany(mappedBy = "userRoles",  fetch = FetchType.EAGER)
    Set<User> users = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}