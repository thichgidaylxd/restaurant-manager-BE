package com.example.restaurant.management.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dish_type_id")
    private DishType dishType;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer sold;

    private String unit;

    private String note;

    @Lob
    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    private Boolean status;

    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist(){
        setCreatedAt(LocalDateTime.now());
        if(unit.isEmpty()) setUnit("Món");
        if(image==null) setImage(null);
        if(status==null) setStatus(true);
        if(sold==null) setSold(0);
    }
}