package com.ecommerce.api.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id")
    )
    Set<UserRole> userRoles = new HashSet<>();

    @Column(name = "name", nullable = false, length = 50)
    String name;

    @Column(name = "surname", nullable = false, length = 50)
    String surname;

    @Column(name = "email", nullable = false, length = 50)
    String email;

    @Column(name = "password", nullable = false, length = 255)
    String password;

    @Column(name = "is_active")
    Boolean isActive;

    @Column(name = "is_delete")
    Boolean isDelete;

    @Column(name = "delete_by")
    Long deleteBy;

    @Column(name = "delete_at")
    LocalDateTime deleteAt;

    @Column(name = "update_at")
    LocalDateTime updateAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}
