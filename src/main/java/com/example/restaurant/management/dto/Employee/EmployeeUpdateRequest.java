package com.example.restaurant.management.dto.Employee;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdateRequest {
    private UUID id;
    private String name;
    private UUID positionId;
    private byte[] image;
    private String address;
    private String phoneNumber;
    private LocalDate birthDate;
}
