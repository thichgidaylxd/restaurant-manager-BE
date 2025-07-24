package com.example.restaurant.management.controller;


import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.entity.DishType;
import com.example.restaurant.management.service.DishTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dish-types")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DishTypeController {

    DishTypeService dishTypeService;

    @GetMapping
    public ApiResponse<List<DishType>> findAll(){
        return ApiResponse.<List<DishType>>builder()
                .data(dishTypeService.findAll())
                .message("Tất loại cả món")
                .build();
    }

    @PostMapping
    public ApiResponse<DishType> createDishType(@RequestBody DishType dishType){
        return ApiResponse.<DishType>builder()
                .data(dishTypeService.createDishType(dishType))
                .message("Thêm loại món thành công")
                .build();
    }

    @PutMapping
    public ApiResponse<DishType> updateDishType(@RequestBody DishType dishType){
        return ApiResponse.<DishType>builder()
                .data(dishTypeService.updateDishType(dishType))
                .message("Cập nhật loại món thành công")
                .build();
    }

    @DeleteMapping("/{dishTypeId}")
    public ApiResponse<DishType> deleteDishType(@PathVariable UUID dishTypeId){
        dishTypeService.deleteById(dishTypeId);
        return ApiResponse.<DishType>builder()
                .message("Xóa loại món thành công")
                .build();
    }

}
