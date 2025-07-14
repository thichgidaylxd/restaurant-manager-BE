package com.example.restaurant.management.controller;

import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.dto.Table.TableCreateRequest;
import com.example.restaurant.management.dto.Table.TableResponse;
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

    TableTypeService tableTypeService;
    TableService tableService;
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public ApiResponse<List<TableResponse>> getAllTable() {

        List<TableResponse> allTables = tableService.findAll();
        messagingTemplate.convertAndSend("/topic/tables", allTables);

        return ApiResponse.<List<TableResponse>>builder()
                .data(allTables)
                .message("Tất cả bàn")
                .build();
    }


//    // Get tableorder by id
//    @GetMapping("/{id}/items")
//    ApiResponse<List<OrderItemRequest>> getTableByName(@PathVariable String id){
//        ApiResponse<List<OrderItemRequest>> apiResponse = new ApiResponse<>();
//        apiResponse.setData(orderItemService.findItemsByTableId(id));
//        return apiResponse;
//    }



    @PostMapping
    public ApiResponse<TableResponse> addTable(@RequestBody TableCreateRequest table) {
        TableResponse newTable = tableService.createTable(table);

        messagingTemplate.convertAndSend("/topic/table-added", newTable);

        return ApiResponse.<TableResponse>builder()
                .data(newTable)
                .message("Thêm bàn thành công")
                .build();
    }

    @PatchMapping("/{tableId}/update-status")
    public ApiResponse<TableResponse> updateTableStatus(@PathVariable UUID tableId, String status){
        TableResponse tableStatusUpdated = tableService.updateTableStatus(tableId,status);
        return ApiResponse.<TableResponse>builder()
                .data(tableStatusUpdated)
                .message("Bàn đã cập nhật trạng thái mới")
                .build();
    }

    @DeleteMapping("/{tableId}")
    public ApiResponse<Tables> deleteTable(@PathVariable UUID tableId) {
        Tables deletedTable = tableService.findById(tableId);

        tableService.deleteTable(tableId);

        messagingTemplate.convertAndSend("/topic/table-deleted", deletedTable);

        return ApiResponse.<Tables>builder()
                .message("Bàn đã xóa thành công")
                .build();
    }
}
