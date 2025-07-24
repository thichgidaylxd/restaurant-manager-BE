package com.example.restaurant.management.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "table_order_id")
    private TableOrder tableOrder;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(nullable = false)
    private Integer quantity;

    private String note;


    private String status;

    @PrePersist
    public void prePersist() {
        if (status == null || status.isBlank()) {
            status = "Chưa gọi";
        }
    }
}