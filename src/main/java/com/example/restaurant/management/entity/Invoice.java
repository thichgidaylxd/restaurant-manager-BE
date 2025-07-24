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
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "table_order_id")
    private TableOrder tableOrder;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @Column(nullable = false)
    private BigDecimal sum;

    private String payMethod;

    private Boolean paid = true;
    @PrePersist
    public void prePersist(){
        if(paid == null){
            setPaid(true);
        }
        setCreatedAt(LocalDateTime.now());
    }


    private LocalDateTime createdAt;
}
