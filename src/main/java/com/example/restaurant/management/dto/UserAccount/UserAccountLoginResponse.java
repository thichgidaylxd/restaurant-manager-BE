package com.example.restaurant.management.dto.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountLoginResponse {
    private Boolean authenticated;
    private String token;
}
