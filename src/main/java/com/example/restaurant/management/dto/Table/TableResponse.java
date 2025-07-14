package com.example.restaurant.management.dto.Table;
import com.example.restaurant.management.entity.TableType;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableResponse {

    private UUID id;
    private TableType tableType;
    private String name;
    private String status;
    private Integer maxPerson;
    private String note;
}
