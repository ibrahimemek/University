package com.example.demo.controller;

import com.example.demo.model.Review;
import com.example.demo.model.ReviewRequest;
import com.example.demo.model.UserInfo;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request) {
        if (!UserInfo.user_type.equals("customer")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        request.setCustomerId(UserInfo.customer_id);
        
        Review review = reviewService.createReview(request);
        if (review == null) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    

    @GetMapping("/restaurantOwnReviews") //restoranın kendi yorumları
    public ResponseEntity<List<Review>> getMyRestaurantReviews() {
        if (!UserInfo.user_type.equals("restaurant")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        List<Review> reviews = reviewService.getRestaurantReviews(UserInfo.restaurant_id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


    @GetMapping("/restaurant/{restaurantId}") // id den çekilen
    public ResponseEntity<List<Review>> getARestaurantReviews(@PathVariable int restaurantId) {
        List<Review> reviews = reviewService.getRestaurantReviews(restaurantId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
    @GetMapping("/customerOwnReviews")
    public ResponseEntity<List<Review>> getCustomerReviews() {
        if (!UserInfo.user_type.equals("customer")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Review> reviews = reviewService.getCustomerReviews(UserInfo.customer_id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
} 