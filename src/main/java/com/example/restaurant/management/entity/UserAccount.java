package com.example.restaurant.management.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_accounts")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserAccount {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    @Size(min = 10, max = 10)
    private String account;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist(){
        setCreatedAt(LocalDateTime.now());
    }
}