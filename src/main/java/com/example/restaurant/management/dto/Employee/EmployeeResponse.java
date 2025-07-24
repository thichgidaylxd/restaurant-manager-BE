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
public class EmployeeResponse {
    private UUID id;
    private UUID positionId;
    private String positionName;
    private String image;
    private String employeeName;
    private String address;
    private LocalDate birthDate;
    private String phoneNumber;
}
