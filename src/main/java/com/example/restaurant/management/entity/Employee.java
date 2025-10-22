package com.example.restaurant.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employees")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Employee {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @Column(nullable = false)
    private String employeeName;

    @JdbcTypeCode(SqlTypes.VARBINARY)
    @Column(name = "image")
    private byte[] image;

    private String address;

    private LocalDate birthDate;

    @Size(min = 10, max = 10, message = "Số điện thoại phải đúng 10 chữ số")
    private String phoneNumber;

    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist(){
        setCreatedAt(LocalDateTime.now());
    }
}