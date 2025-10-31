package com.example.demo.service;

import com.example.demo.model.Restaurant;
import com.example.demo.model.UserInfo;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.RestaurantRepo;
import com.example.demo.repository.OrderRepo;
import com.example.demo.repository.MenuItemRepo;
import com.example.demo.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantService {
    private final RestaurantRepo restaurantRepo;
    private final OrderRepo orderRepository;
    private final OrderService orderService;
    private final MenuItemRepo menuItemRepo;
    @Autowired
    public RestaurantService(RestaurantRepo restaurantRepo, OrderRepo orderRepository, OrderService orderService, MenuItemRepo menuItemRepo) {
        this.restaurantRepo = restaurantRepo;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.menuItemRepo = menuItemRepo;
    }
    public Restaurant createRestaurant(int user_id, String contact, String address, String name,
                                       int average_delivery, String business_hours, String status, String cuisine, int min_amount) {
        Restaurant restaurant = new Restaurant(user_id, contact, address, name, average_delivery, business_hours, status, cuisine, min_amount);
        restaurantRepo.save(restaurant);
        UserInfo.restaurant_id = restaurant.getRestaurant_id();
        return restaurant;
    }
    public List<Restaurant> getRestaurantsByName(String name) {
        List<Restaurant> restaurants = restaurantRepo.findRestaurantsByNameContainingIgnoreCase(name);
        name.replace("ç", "c").replace("Ç", "C")
                .replace("ğ", "g").replace("Ğ", "G")
                .replace("ı", "i").replace("İ", "I")
                .replace("ö", "o").replace("Ö", "O")
                .replace("ş", "s").replace("Ş", "S")
                .replace("ü", "u").replace("Ü", "U");
        List<Restaurant> restaurants2 = restaurantRepo.findRestaurantsByNameContainingIgnoreCase(name);
        Set<Restaurant> mergedRestaurant = new LinkedHashSet<>();
        mergedRestaurant.addAll(restaurants);
        mergedRestaurant.addAll(restaurants2);
        return new ArrayList<>(mergedRestaurant);
    }
    public List<Restaurant> getAll() {
        return restaurantRepo.findAll();
    }
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepo.findByCuisineContaining(cuisine);
    }
    @Transactional(readOnly = true)
    public List<Order> findPendingOrdersForRestaurant(Integer restaurantId) {
        return orderRepository.findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.PENDING_RESTAURANT_CONFIRMATION);
    }
    @Transactional
    public Order confirmOrder(Integer orderId, Integer restaurantId) {
        return orderService.confirmOrderByRestaurant(orderId, restaurantId);
    }
    @Transactional
    public Order rejectOrder(Integer orderId, Integer restaurantId) {
        return orderService.rejectOrderByRestaurant(orderId, restaurantId);
    }
    public List<Order> findAcceptedOrdersForRestaurant(Integer restaurantId) {
        return restaurantRepo.findAcceptedOrdersForRestaurant(restaurantId);
    }

}