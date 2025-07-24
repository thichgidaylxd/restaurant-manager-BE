package com.example.restaurant.management.service;


import com.example.restaurant.management.dto.Role.RoleResponse;
import com.example.restaurant.management.entity.Role;
import com.example.restaurant.management.exception.AppException;
import com.example.restaurant.management.exception.ErrorCode;
import com.example.restaurant.management.repository.RoleRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepo roleRepo;

    public List<RoleResponse> findAll(){
        return roleRepo.findAll().stream().map(role -> {
            return RoleResponse.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .build();
        }).toList();
    }


    public RoleResponse createRole(Role role){
        if(roleRepo.existsByName(role.getName())) throw new AppException(ErrorCode.ROLE_EXISTED);
        Role newRole = roleRepo.save(role);
        return RoleResponse.builder()
                .id(newRole.getId())
                .name(newRole.getName())
                .build();
    }

    public void deleteById(UUID roleId){
        roleRepo.deleteById(roleId);
    }

}
