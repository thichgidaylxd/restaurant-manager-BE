package com.example.restaurant.management.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "permissions")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Permission {
    @Id @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private Boolean isAccepted;

    private String note;

    private LocalDateTime createdAt = LocalDateTime.now();
}