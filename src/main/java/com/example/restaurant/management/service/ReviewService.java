package com.example.restaurant.management.service;

import com.example.restaurant.management.entity.Review;
import com.example.restaurant.management.entity.UserAccount;
import com.example.restaurant.management.exception.AppException;
import com.example.restaurant.management.exception.ErrorCode;
import com.example.restaurant.management.repository.ReviewRepo;
import com.example.restaurant.management.repository.UserAccountRepo;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {
    ReviewRepo reviewRepo;
    UserAccountRepo  userAccountRepo;

    public Review createReview(UUID userId, String content, Integer starRating) {
        Review review = new Review();
        UserAccount user =  userAccountRepo.findById(userId).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        review.setUser(user);
        if(content!=null)
            review.setContent(content);
        if(starRating!=null)
            review.setStarRating(starRating);
        return reviewRepo.save(review);
    }

    public List<Review> getAll(){
        return reviewRepo.findAll();
    }


}
