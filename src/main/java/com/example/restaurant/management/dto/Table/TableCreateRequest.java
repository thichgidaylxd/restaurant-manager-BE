package com.example.restaurant.management.dto.Table;

import com.example.restaurant.management.entity.TableType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableCreateRequest {
    private String name;
    private TableType tableType;
    private Integer maxPerson;
    private String note;
}

