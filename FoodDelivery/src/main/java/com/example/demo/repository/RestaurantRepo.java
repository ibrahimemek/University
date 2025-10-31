package com.example.demo.repository;
import com.example.demo.model.UserHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Integer>
{
    List<Restaurant> findRestaurantsByName(String name);
    @Query("SELECT r FROM Restaurant r WHERE r.cuisine LIKE %:cuisine%")
    List<Restaurant> findByCuisineContaining(@Param("cuisine") String cuisine);

    @Query("SELECT r FROM Restaurant r WHERE r.user_id = :user_id")
    List<Restaurant> findRestaurantByUserId(@Param("user_id") Integer user_id);

    @Query("SELECT o FROM Order o WHERE o.restaurantId = :restaurantId AND o.orderStatus = 'PENDING_ASSIGNMENT'")
    List<Order> findAcceptedOrdersForRestaurant(@Param("restaurantId") Integer restaurantId);

    List<Restaurant> findRestaurantsByNameContainingIgnoreCase(String name);



}
