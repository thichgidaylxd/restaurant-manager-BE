package com.example.restaurant.management.repository;

import com.example.restaurant.management.entity.OrderItem;
import com.example.restaurant.management.entity.TableOrder;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findByTableOrder_Table_Id(UUID tableId);
    Optional findByTableOrder_IdAndDish_Id(UUID tableOrderId, UUID dishId);
    void deleteAllByTableOrder_Id(UUID tableOrderId);
    @Modifying
    @Transactional
    @Query("UPDATE OrderItem o SET o.status = :status WHERE o.id = :orderItemId")
    void updateStatusById(@Param("orderItemId") UUID orderItemId, @Param("status") String status);
}
