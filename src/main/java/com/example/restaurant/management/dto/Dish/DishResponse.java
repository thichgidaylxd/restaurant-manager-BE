package com.example.restaurant.management.dto.Dish;

import com.example.restaurant.management.entity.DishType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishResponse {

    private UUID id;

    private DishType dishType;

    private String name;

    private BigDecimal price;

    private Integer sold;

    private String unit;

    private String note;

    private String image;

    private Boolean status;

    private LocalDateTime createdAt;
}
