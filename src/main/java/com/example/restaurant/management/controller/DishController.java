package com.example.restaurant.management.controller;


import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.dto.Dish.DishResponse;
import com.example.restaurant.management.entity.Dish;
import com.example.restaurant.management.service.DishService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dishes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DishController {

    DishService dishService;

    @GetMapping
    public ApiResponse<List<DishResponse>> findByDishTypeId(@RequestParam(required = false) UUID dishTypeId){
        List<DishResponse> dishes = (dishTypeId!=null)
                ? dishService.findByDishType_Id(dishTypeId)
                : dishService.findAll();
        return ApiResponse.<List<DishResponse>>builder()
                .data(dishes)
                .message((dishTypeId!=null)
                        ? "Món theo loại món"
                        : "Tất cả món")
                .build();
    }

    @PostMapping
    public ApiResponse<DishResponse> createDish(@RequestBody Dish dish){
        DishResponse newDish = dishService.createDish(dish);
        return ApiResponse.<DishResponse>builder()
                .data(newDish)
                .message("Thêm món thành công")
                .build();
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(Principal principal) {
        return ResponseEntity.ok("Xin chào " + principal.getName());
    }


    @PutMapping("/{dishId}")
    public ApiResponse<DishResponse> updateDish(@PathVariable UUID dishId,@RequestBody Dish dish){
        DishResponse newDish = dishService.updateDish(dishId,dish);
        return ApiResponse.<DishResponse>builder()
                .data(newDish)
                .message("Cập nhật món thành công")
                .build();
    }


    @DeleteMapping("/{dishId}")
    public ApiResponse<Dish> deleteDish(@PathVariable UUID dishId){
        dishService.deleteDish(dishId);
        return ApiResponse.<Dish>builder()
                .message("Món đã xóa thành công")
                .build();
    }


}
