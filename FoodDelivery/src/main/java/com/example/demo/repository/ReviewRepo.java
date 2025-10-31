package com.example.demo.repository;

import com.example.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
    
    @Query("SELECT r FROM Review r WHERE r.restaurant_id = :restaurantId")
    List<Review> findByRestaurantId(@Param("restaurantId") Integer restaurantId);
    
    @Query("SELECT r FROM Review r WHERE r.customer_id = :customerId")
    List<Review> findByCustomerId(@Param("customerId") Integer customerId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.restaurant_id = :restaurantId")
    Double getAverageRatingForRestaurant(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT r FROM Review r WHERE r.order_id = :orderId")
    List<Review> findByOrderId(@Param("orderId") Integer orderId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.order_id = :orderId")
    long countByOrder_id(@Param("orderId") Integer orderId);

} 