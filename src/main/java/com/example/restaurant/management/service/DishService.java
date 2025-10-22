package com.example.restaurant.management.service;


import com.example.restaurant.management.dto.Dish.DishRequest;
import com.example.restaurant.management.dto.Dish.DishResponse;
import com.example.restaurant.management.entity.Dish;
import com.example.restaurant.management.entity.DishType;
import com.example.restaurant.management.exception.AppException;
import com.example.restaurant.management.exception.ErrorCode;
import com.example.restaurant.management.repository.DishRepo;
import com.example.restaurant.management.repository.DishTypeRepo;
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
public class DishService {
    DishRepo dishRepo;
    DishTypeRepo dishTypeRepo;

    public List<DishResponse> findAll(){
        return dishRepo.findAll().stream()
                .map(Builder::toDishResponse)
                .toList();
    }

    public List<DishResponse> findByDishType_Id(UUID dishTypeId){
        dishTypeRepo.findById(dishTypeId).orElseThrow(()->new AppException(ErrorCode.DISHTYPE_NOT_FOUND));
        return dishRepo.findAllByDishType_id(dishTypeId).stream()
                .map(Builder::toDishResponse)
                .toList();
    }

    public Dish findById(UUID dishId){
        return dishRepo.findById(dishId)
                .orElseThrow(()-> new AppException(ErrorCode.DISH_NOT_FOUND));
    }



    public DishResponse createDish(DishRequest dishRequest){
        // Kiểm tra dishType tồn tại
        DishType dishType = dishTypeRepo.findById(dishRequest.getDishType().getId())
                .orElseThrow(() -> new AppException(ErrorCode.DISHTYPE_NOT_FOUND));

        // Kiểm tra tên món đã tồn tại
        if(dishRepo.existsByName(dishRequest.getName())) {
            throw new AppException(ErrorCode.DISH_EXISTED);
        }

        // Convert Base64 string thành byte[]
        byte[] imageBytes = null;
        if(dishRequest.getImageBase64() != null && !dishRequest.getImageBase64().isEmpty()) {
            try {
                // Loại bỏ prefix "data:image/...;base64," nếu có
                String base64Image = dishRequest.getImageBase64();
                if(base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }
                imageBytes = Base64.getDecoder().decode(base64Image);
            } catch (IllegalArgumentException e) {
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
        }

        // Tạo entity Dish
        Dish dish = Dish.builder()
                .dishType(dishType)
                .name(dishRequest.getName())
                .price(dishRequest.getPrice())
                .unit(dishRequest.getUnit())
                .note(dishRequest.getNote())
                .image(imageBytes)  // Set byte[] đã decode
                .build();

        return Builder.toDishResponse(dishRepo.save(dish));
    }

    public DishResponse updateDish(UUID dishId, Dish updatedDish) {
        Dish existingDish = dishRepo.findById(dishId)
                .orElseThrow(() -> new AppException(ErrorCode.DISH_NOT_FOUND));

        // Kiểm tra loại món ăn có tồn tại không
        DishType dishType = dishTypeRepo.findById(updatedDish.getDishType().getId())
                .orElseThrow(() -> new AppException(ErrorCode.DISHTYPE_NOT_FOUND));

        // Cập nhật thông tin
        existingDish.setDishType(dishType);
        existingDish.setName(updatedDish.getName());
        existingDish.setPrice(updatedDish.getPrice());
        existingDish.setUnit(updatedDish.getUnit());
        existingDish.setNote(updatedDish.getNote());
        existingDish.setImage(updatedDish.getImage());
        existingDish.setStatus(updatedDish.getStatus());

        // Lưu và trả về kết quả
        return Builder.toDishResponse(dishRepo.save(existingDish));
    }


    public void deleteDish(UUID dishId){
        dishRepo.deleteById(dishId);
    }

}
