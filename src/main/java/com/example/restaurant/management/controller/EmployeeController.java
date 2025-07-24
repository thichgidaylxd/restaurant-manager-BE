package com.example.restaurant.management.controller;


import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.dto.Employee.EmployeeCreateRequest;
import com.example.restaurant.management.dto.Employee.EmployeeResponse;
import com.example.restaurant.management.dto.Employee.EmployeeUpdateRequest;
import com.example.restaurant.management.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {
    EmployeeService employeeService;

    @GetMapping
    public ApiResponse<List<EmployeeResponse>> findAll(){
        return ApiResponse.<List<EmployeeResponse>>builder()
                .data(employeeService.findAll())
                .message("Tất cả nhân viên")
                .build();
    }


    @PostMapping
    public ApiResponse<EmployeeResponse> createEmployee(@RequestBody EmployeeCreateRequest request){
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.createEmployee(request))
                .message("Thêm nhân viên thành công")
                .build();
    }


    @PutMapping
    public ApiResponse<EmployeeResponse> updateEmployee(@RequestBody EmployeeUpdateRequest request){
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.updateEmployee(request))
                .message("Cập nhật nhân viên thành công")
                .build();
    }


    @DeleteMapping("/{employeeId}")
    public ApiResponse<String> deleteById(@PathVariable UUID employeeId){
        employeeService.deleteById(employeeId);
        return ApiResponse.<String>builder()
                .message("Xóa nhân viên thành công")
                .build();
    }

}
