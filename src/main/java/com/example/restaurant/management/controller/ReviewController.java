package com.example.restaurant.management.controller;

import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.entity.Review;
import com.example.restaurant.management.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {
    ReviewService reviewService;

    @GetMapping
    public ApiResponse<List<Review>> getAllReviews(){
        return ApiResponse.<List<Review>>builder()
                .message("Tất cả đánh giá")
                .data(reviewService.getAll())
                .build();
    }

    @PostMapping
    public ApiResponse<Review> createReview(@RequestParam UUID userId, @RequestParam String content, @RequestParam Integer ratingStar){
        return ApiResponse.<Review>builder()
                .message("Tạo đánh giá thành công")
                .data(reviewService.createReview(userId,content,ratingStar))
                .build();
    }
}
