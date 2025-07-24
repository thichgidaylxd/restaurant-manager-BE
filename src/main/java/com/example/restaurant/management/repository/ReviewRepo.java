package com.example.restaurant.management.repository;

import com.example.restaurant.management.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepo extends JpaRepository<Review, UUID> {}