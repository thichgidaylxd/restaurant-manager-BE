package com.example.restaurant.management.dto.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountRegisterRequest {
    private String accountName;
    private String account;
    private UUID roleId;
    private String password;
    private String confirmPassword;
}
