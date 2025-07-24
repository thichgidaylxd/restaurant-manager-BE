package com.example.restaurant.management.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "positions")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Position {
    @Id @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String note;

    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist(){
        setCreatedAt(LocalDateTime.now());
    }
}