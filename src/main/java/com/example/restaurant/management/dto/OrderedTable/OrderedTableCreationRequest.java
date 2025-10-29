package com.example.restaurant.management.dto.OrderedTable;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderedTableCreationRequest {
    String phone;
    String name;
    LocalDateTime orderedTime;
    Integer personNumber;
    UUID tableId;
}
