package com.example.demo.repository;

import com.example.demo.model.FavoriteRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRestaurantRepo extends JpaRepository<FavoriteRestaurant, Integer> {
    @Query("SELECT f FROM FavoriteRestaurant f WHERE f.customer.customer_id = :customer_id")
    List<FavoriteRestaurant> findByCustomerId(@Param("customer_id") Integer customer_id);

    @Query("SELECT f FROM FavoriteRestaurant f WHERE f.restaurantId = :restaurantId")
    List<FavoriteRestaurant> findByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT f FROM FavoriteRestaurant f WHERE f.customer.customer_id = :customerId AND f.restaurantId = :restaurantId")
    FavoriteRestaurant findByCustomerIdAndRestaurantId(@Param("customerId") int customerId, @Param("restaurantId") int restaurantId);
}