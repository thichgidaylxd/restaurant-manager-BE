package com.example.restaurant.management.repository;

import com.example.restaurant.management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
    Boolean existsByName(String name);
}