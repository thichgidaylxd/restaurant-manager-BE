package com.example.restaurant.management.controller;


import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.entity.Position;
import com.example.restaurant.management.service.PositionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionController {
    PositionService positionService;

    @GetMapping
    public ApiResponse<List<Position>> findAll(){
        return ApiResponse.<List<Position>>builder()
                .data(positionService.findAll())
                .message("Tất cả chức vụ")
                .build();
    }

    @PostMapping
    public ApiResponse<Position> createPositon(@RequestBody Position position){
        return ApiResponse.<Position>builder()
                .data(positionService.createPosition(position))
                .message("Tạo chức vụ mới thành công")
                .build();
    }

    @DeleteMapping("/{positionId}")
    public ApiResponse<String> deleteById(@PathVariable UUID positionId){
        positionService.deleteById(positionId);
        return ApiResponse.<String>builder()
                .message("Xóa chức vụ thành công")
                .build();
    }
}
