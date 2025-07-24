package com.example.restaurant.management.controller;

import com.example.restaurant.management.dto.ApiRe.ApiResponse;

import com.example.restaurant.management.dto.UserAccount.*;
import com.example.restaurant.management.service.UserAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserAccountController {
    UserAccountService userAccountService;


    @GetMapping
    public ApiResponse<List<UserAccountResponse>> findAll(){
        return ApiResponse.<List<UserAccountResponse>>builder()
                .data(userAccountService.findAll())
                .message("Tất cả tài khoản")
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<UserAccountLoginResponse> login (@RequestBody UserAccountLogin userAccountLogin){
        return ApiResponse.<UserAccountLoginResponse>builder()
                .data(userAccountService.login(userAccountLogin))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<UserAccountRegisterResponse> register(@RequestBody UserAccountRegisterRequest request){
        System.out.println(request);
        return ApiResponse.<UserAccountRegisterResponse>builder()
                .data(userAccountService.register(request))
                .message("Đăng ký tài khoản thành công")
                .build();
    }


    @DeleteMapping("/{userAccountId}")
    public ApiResponse<String> deleteUserById(@PathVariable UUID userAccountId){
        userAccountService.deleteById(userAccountId);
        return ApiResponse.<String>builder()
                .message("Xóa tài khoản thành công")
                .build();
    }
}
