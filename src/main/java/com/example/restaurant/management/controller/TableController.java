package com.example.restaurant.management.controller;

import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.dto.Table.TableCreateRequest;
import com.example.restaurant.management.dto.Table.TableResponse;
import com.example.restaurant.management.dto.Table.TableUpdateStatus;
import com.example.restaurant.management.entity.Tables;
import com.example.restaurant.management.service.TableService;
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
@RequestMapping("/tables")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TableController {

    TableService tableService;


    @GetMapping
    public ApiResponse<List<TableResponse>> getAllTable() {
        List<TableResponse> allTables = tableService.findAll();
        return ApiResponse.<List<TableResponse>>builder()
                .data(allTables)
                .message("Tất cả bàn")
                .build();
    }



    @PostMapping
    public ApiResponse<TableResponse> addTable(@RequestBody TableCreateRequest table) {
        TableResponse newTable = tableService.createTable(table);


        return ApiResponse.<TableResponse>builder()
                .data(newTable)
                .message("Thêm bàn thành công")
                .build();
    }

    @PatchMapping("/{tableId}/update-status")
    public ApiResponse<TableResponse> updateTableStatus(@PathVariable UUID tableId, @RequestBody TableUpdateStatus tableUpdateStatus){
        TableResponse tableStatusUpdated = tableService.updateTableStatus(tableId,tableUpdateStatus.getStatus());
        return ApiResponse.<TableResponse>builder()
                .data(tableStatusUpdated)
                .message("Bàn đã cập nhật trạng thái mới")
                .build();
    }

    @DeleteMapping("/{tableId}")
    public ApiResponse<Tables> deleteTable(@PathVariable UUID tableId) {
        Tables deletedTable = tableService.findById(tableId);

        tableService.deleteTable(tableId);

        return ApiResponse.<Tables>builder()
                .message("Bàn đã xóa thành công")
                .build();
    }
}
