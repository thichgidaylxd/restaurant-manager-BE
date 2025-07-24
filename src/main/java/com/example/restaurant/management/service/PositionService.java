package com.example.restaurant.management.service;


import com.example.restaurant.management.entity.Position;
import com.example.restaurant.management.exception.AppException;
import com.example.restaurant.management.exception.ErrorCode;
import com.example.restaurant.management.repository.PositionRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionService {
    PositionRepo positionRepo;

    public List<Position> findAll(){
        return positionRepo.findAll();
    }


    public Position createPosition(Position position){
        if(positionRepo.existsByName(position.getName())) throw new AppException(ErrorCode.POSITION_EXISTED);
        return positionRepo.save(position);
    }

    public void deleteById(UUID positionId){
        positionRepo.deleteById(positionId);
    }
}
