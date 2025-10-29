package com.example.restaurant.management.repository;

import com.example.restaurant.management.entity.OrderedTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderedTableRepo extends JpaRepository<OrderedTable, UUID> {
}
