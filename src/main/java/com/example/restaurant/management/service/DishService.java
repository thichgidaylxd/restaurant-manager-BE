package com.example.restaurant.management.service;


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


    public DishResponse createDish(Dish dish){
        dishTypeRepo.findById(dish.getDishType().getId())
                .orElseThrow(()->new AppException(ErrorCode.DISHTYPE_NOT_FOUND));
        if(dishRepo.existsByName(dish.getName())) throw new AppException((ErrorCode.DISH_EXISTED));

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
