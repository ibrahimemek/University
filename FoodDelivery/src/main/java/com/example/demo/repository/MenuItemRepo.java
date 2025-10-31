package com.example.demo.repository;

import com.example.demo.model.MenuItem;
import com.example.demo.model.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem, Integer>
{

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant_id = :restaurant_id")
    List<MenuItem> findByRestaurantId(@Param("restaurant_id") Integer restaurant_id);

}
