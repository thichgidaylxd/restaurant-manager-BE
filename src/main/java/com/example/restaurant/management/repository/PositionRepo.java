package com.example.restaurant.management.repository;

import com.example.restaurant.management.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PositionRepo extends JpaRepository<Position, UUID> {
    Boolean existsByName(String name);

}