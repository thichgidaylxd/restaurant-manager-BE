package com.example.restaurant.management.dto.UserAccount;

import com.example.restaurant.management.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountRegisterResponse {
        private UUID id;
        private String roleName;

        private String accountName;

        private String account;

        private LocalDateTime createdAt;
}
