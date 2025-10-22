package com.example.restaurant.management.dto.Dish;

import com.example.restaurant.management.entity.DishType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DishRequest {
    private DishType dishType;
    private String name;
    private BigDecimal price;
    private String unit;
    private String note;
    private String imageBase64;  // Nhận Base64 string từ frontend
}