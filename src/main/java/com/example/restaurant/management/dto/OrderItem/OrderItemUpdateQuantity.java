package com.example.restaurant.management.dto.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemUpdateQuantity {
    private UUID orderItemId;
    private Integer quantity;
}
