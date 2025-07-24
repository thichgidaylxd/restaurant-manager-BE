package com.example.restaurant.management.controller;

import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.entity.Revenue;
import com.example.restaurant.management.service.RevenueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/revenue")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RevenueController {

    RevenueService revenueService;


    @GetMapping("/day")
    public ApiResponse<Revenue> getRevenueByDay(@RequestParam("date") LocalDate date) {
        Revenue data = revenueService.getRevenueByDay(date);
        return ApiResponse.<Revenue>builder()
                .data(data)
                .message("Doanh thu theo ngày " + date)
                .build();
    }

    // 💰 GET /revenue/week?date=2025-07-27
    @GetMapping("/week")
    public ApiResponse<List<Revenue>> getRevenueByWeek(@RequestParam("date") LocalDate date) {
        List<Revenue> data = revenueService.getRevenueByWeek(date);
        return ApiResponse.<List<Revenue>>builder()
                .data(data)
                .message("Doanh thu theo tuần chứa ngày " + date)
                .build();
    }

    // 💰 GET /revenue/month?month=2025-07
    @GetMapping("/month")
    public ApiResponse<List<Revenue>> getRevenueByMonth(@RequestParam("month") YearMonth month) {
        List<Revenue> data = revenueService.getRevenueByMonth(month);
        return ApiResponse.<List<Revenue>>builder()
                .data(data)
                .message("Doanh thu tháng " + month)
                .build();
    }
}
