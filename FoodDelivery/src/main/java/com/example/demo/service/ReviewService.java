package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.Review;
import com.example.demo.model.ReviewRequest;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.ReviewRepo;
import com.example.demo.repository.RestaurantRepo;
import com.example.demo.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private final ReviewRepo reviewRepo;
    @Autowired
    private final RestaurantRepo restaurantRepo;
    @Autowired
    private final OrderRepo orderRepo;

    
    @Autowired
    public ReviewService(ReviewRepo reviewRepo, RestaurantRepo restaurantRepo, OrderRepo orderRepo) {
        this.reviewRepo = reviewRepo;
        this.restaurantRepo = restaurantRepo;
        this.orderRepo = orderRepo;
    }
    
    @Transactional
    public Review createReview(ReviewRequest request) {
        if(reviewRepo.countByOrder_id(request.getOrderId()) >= 1) return null;
        Order order = orderRepo.findById(request.getOrderId()).orElse(null);
        if (order == null || !order.getOrderStatus().equals(OrderStatus.DELIVERED)) { // yorum yapabilmesi için ulaşması laızm
            throw new IllegalArgumentException();
        }
        if (order.getCustomerId() != UserInfo.customer_id) { // siparişi verenle aynı id olmak zorunda
            throw new IllegalArgumentException();
        }

        Review review = new Review(UserInfo.customer_id, order.getRestaurantId(),
                                 request.getOrderId(), request.getRating(), request.getComment());
        reviewRepo.save(review); // burada kaydedip aşağıda ortalama günceliyoruz
        Double avgRating = reviewRepo.getAverageRatingForRestaurant(order.getRestaurantId()); // .
        if (avgRating != null) {
            restaurantRepo.findById(order.getRestaurantId()).ifPresent(restaurant -> {
                restaurant.setRating(avgRating.floatValue());
                restaurantRepo.save(restaurant);
            });
        }
        
        return review;
    }
    
    public List<Review> getRestaurantReviews(int restaurantId) {
        return reviewRepo.findByRestaurantId(restaurantId);
    }
    public List<Review> getCustomerReviews(int customer_id) {
        return reviewRepo.findByCustomerId(customer_id);
    }

} 