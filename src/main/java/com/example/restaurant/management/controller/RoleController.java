package com.example.restaurant.management.controller;

import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.dto.Role.RoleResponse;
import com.example.restaurant.management.entity.Role;
import com.example.restaurant.management.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @GetMapping
    public ApiResponse<List<RoleResponse>> findAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.findAll())
                .message("Danh sách tất cả vai trò")
                .build();
    }

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody Role role) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.createRole(role))
                .message("Tạo vai trò thành công")
                .build();
    }

    @DeleteMapping("/{roleId}")
    public ApiResponse<String> deleteRole(@PathVariable UUID roleId) {
        roleService.deleteById(roleId);
        return ApiResponse.<String>builder()
                .message("Xóa vai trò thành công")
                .build();
    }
}
