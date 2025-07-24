package com.example.restaurant.management.dto.invoice;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceDishRespone {
    private UUID invoiceId;//
    private UUID dishId;//
    private String dishName;
    private BigDecimal price;
    private String unit;
    private byte[] image;
    private Integer quantity;//
}
