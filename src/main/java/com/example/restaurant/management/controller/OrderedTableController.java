package com.example.restaurant.management.controller;

import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.dto.OrderedTable.OrderedTableCreationRequest;
import com.example.restaurant.management.entity.OrderedTable;
import com.example.restaurant.management.service.OrderedTableService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ordered-table")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderedTableController {

    OrderedTableService orderedTableService;

    @PostMapping
    public ApiResponse<OrderedTable> createOrderedTable(@RequestBody OrderedTableCreationRequest request) {
        return ApiResponse.<OrderedTable>builder()
                .message("Tạo yêu cầu đặt bàn thành công")
                .data(orderedTableService.createOrderedTable(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderedTable>> getAllOrderedTables() {
        return ApiResponse.<List<OrderedTable>>builder()
                .message("Danh sách tất cả yêu cầu đặt bàn")
                .data(orderedTableService.findAll())
                .build();
    }

    @PutMapping("/{id}/approve")
    public ApiResponse<OrderedTable> approveOrderedTable(@PathVariable UUID id) {
        return ApiResponse.<OrderedTable>builder()
                .message("Yêu cầu đặt bàn đã được duyệt")
                .data(orderedTableService.Approve(id))
                .build();
    }

    @PutMapping("/{id}/reject")
    public ApiResponse<OrderedTable> rejectOrderedTable(@PathVariable UUID id) {
        return ApiResponse.<OrderedTable>builder()
                .message("Yêu cầu đặt bàn đã bị từ chối")
                .data(orderedTableService.reject(id))
                .build();
    }
}
