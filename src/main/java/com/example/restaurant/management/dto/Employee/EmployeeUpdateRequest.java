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
    private String employeeName;
    private String imageBase64;  // Đổi từ image sang imageBase64
    private String phoneNumber;
    private UUID positionId;
    private String address;
    private LocalDate birthDate;
}