package com.example.restaurant.management.repository;

import com.example.restaurant.management.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepo extends JpaRepository<Permission, UUID> {}