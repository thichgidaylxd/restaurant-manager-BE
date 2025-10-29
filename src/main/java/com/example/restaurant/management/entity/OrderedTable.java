package com.example.restaurant.management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ordered_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderedTable {
    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String phone;
    String name;
    LocalDateTime orderedTime;
    Integer personNumber;
    String status;
    UUID tableId;
}
