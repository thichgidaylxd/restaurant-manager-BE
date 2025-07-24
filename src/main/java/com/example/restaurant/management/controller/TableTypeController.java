package com.example.restaurant.management.controller;

import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.entity.TableType;
import com.example.restaurant.management.service.TableTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/tables/type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TableTypeController {

    TableTypeService tableTypeService;

    @GetMapping
    public ApiResponse<List<TableType>> findAllTable() {
        List<TableType> tableTypes = tableTypeService.findAll();
        return ApiResponse.<List<TableType>>builder()
                .data(tableTypes)
                .message("Danh sách loại bàn")
                .build();
    }

    @PostMapping
    public ApiResponse<TableType> addTableType(@RequestBody TableType tableType) {
        TableType newTableType = tableTypeService.createTaleType(tableType);
        return ApiResponse.<TableType>builder()
                .data(newTableType)
                .message("Thêm loại bàn thành công")
                .build();
    }

    @DeleteMapping("/{tableTypeId}")
    public ApiResponse<TableType> deleteTable(@PathVariable UUID tableTypeId) {
        tableTypeService.deleteById(tableTypeId);
        return ApiResponse.<TableType>builder()
                .message("Xóa loại bàn thành công")
                .build();
    }
}
