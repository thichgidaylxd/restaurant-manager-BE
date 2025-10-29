package com.example.restaurant.management.service;

import com.example.restaurant.management.dto.Employee.EmployeeCreateRequest;
import com.example.restaurant.management.dto.Employee.EmployeeResponse;
import com.example.restaurant.management.dto.Employee.EmployeeUpdateRequest;
import com.example.restaurant.management.entity.Employee;
import com.example.restaurant.management.entity.Position;
import com.example.restaurant.management.exception.AppException;
import com.example.restaurant.management.exception.ErrorCode;
import com.example.restaurant.management.repository.EmployeeRepo;
import com.example.restaurant.management.repository.PositionRepo;
import com.example.restaurant.management.util.Builder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {
    EmployeeRepo employeeRepo;
    PositionRepo positionRepo;

    public List<EmployeeResponse> findAll(){
        return employeeRepo.findAll().stream()
                .map(Builder::toEmployeeResponse)
                .toList();
    }


    public EmployeeResponse createEmployee(EmployeeCreateRequest request){
        Position position = positionRepo.findById(request.getPositionId())
                .orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_EXISTED));

        // Convert Base64 string thành byte[]
        byte[] imageBytes = null;
        if(request.getImageBase64() != null && !request.getImageBase64().isEmpty()) {
            try {
                // Loại bỏ prefix "data:image/...;base64," nếu có
                String base64Image = request.getImageBase64();
                if(base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }
                imageBytes = Base64.getDecoder().decode(base64Image);
            } catch (IllegalArgumentException e) {
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
        }

        Employee employee = Employee.builder()
                .employeeName(request.getEmployeeName())
                .image(imageBytes)  // Set byte[] đã decode
                .phoneNumber(request.getPhoneNumber())
                .position(position)
                .address(request.getAddress())
                .birthDate(request.getBirthDate())
                .build();

        return Builder.toEmployeeResponse(employeeRepo.save(employee));
    }

    public EmployeeResponse updateEmployee(EmployeeUpdateRequest request){
        Position position = positionRepo.findById(request.getPositionId())
                .orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_EXISTED));

        Employee employee = employeeRepo.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // Convert Base64 string thành byte[]
        byte[] imageBytes = null;
        if(request.getImageBase64() != null && !request.getImageBase64().isEmpty()) {
            try {
                // Loại bỏ prefix "data:image/...;base64," nếu có
                String base64Image = request.getImageBase64();
                if(base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }
                imageBytes = Base64.getDecoder().decode(base64Image);
            } catch (IllegalArgumentException e) {
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
        }

        employee.setEmployeeName(request.getEmployeeName());
        employee.setPosition(position);
        employee.setImage(imageBytes);  // Set byte[] đã decode
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setAddress(request.getAddress());
        employee.setBirthDate(request.getBirthDate());

        employeeRepo.save(employee);

        return Builder.toEmployeeResponse(employee);
    }


    public void deleteById(UUID employeeId){
        employeeRepo.deleteById(employeeId);
    }

}
