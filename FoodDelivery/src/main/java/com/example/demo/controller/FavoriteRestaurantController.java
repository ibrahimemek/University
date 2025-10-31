package com.example.demo.controller;

import com.example.demo.model.FavoriteRestaurant;
import com.example.demo.service.FavoriteRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/favorites")
public class FavoriteRestaurantController {

    @Autowired
    FavoriteRestaurantService service;

    @PostMapping("/add/{restaurantId}")
    public FavoriteRestaurant addFavorite(@PathVariable int restaurantId) {
        return service.addFavorite(restaurantId);
    }

    @GetMapping("/getAll")
    public List<FavoriteRestaurant> getAllFavorites() {
        return service.allFavorites();
    }

    @DeleteMapping("/delete/{restaurantId}")
    public boolean deleteFavoriteByRestaurantId(@PathVariable int restaurantId) {
        return service.deleteFavoriteByRestaurantId(restaurantId);
    }

    @GetMapping("/check/{restaurantId}")
    public boolean checkIfFavorite(@PathVariable int restaurantId) {
        return service.isFavorite(restaurantId);
    }
}