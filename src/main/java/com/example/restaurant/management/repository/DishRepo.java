package com.example.restaurant.management.repository;

import com.example.restaurant.management.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface DishRepo extends JpaRepository<Dish, UUID> {
    List<Dish> findAllByDishType_id(UUID dishTypeId);
    Boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Dish d SET d.sold = d.sold + :sold WHERE d.id = :dishId")
    void addSoldById(@Param("dishId") UUID dishId, @Param("sold") Integer sold);

}
