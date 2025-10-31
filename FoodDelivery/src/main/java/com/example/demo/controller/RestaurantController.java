package com.example.demo.controller;

import com.example.demo.model.Restaurant;
import com.example.demo.model.SearchRequest;
import com.example.demo.model.UserInfo;
import com.example.demo.model.Order;
import com.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(UserInfo.user_id, restaurant.getContact(),
                restaurant.getAddress(), restaurant.getName(), restaurant.getAvg_delivery(),
                restaurant.getBusiness_hours(), restaurant.getStatus(), restaurant.getCuisine(), restaurant.getMin_amount());
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public List<Restaurant> searchRestaurant(@RequestBody SearchRequest request) {
        List<Restaurant> allList = restaurantService.getAll();
        List<Restaurant> nameRest = (request.getName() != null) ? restaurantService.getRestaurantsByName(request.getName()) : allList;
        List<Restaurant> cuisineRest = (request.getCuisine() != null) ? restaurantService.getRestaurantsByCuisine(request.getCuisine()) : allList;
        List<Restaurant> result = new ArrayList<>();
        if (nameRest.equals(allList)) {
            result = cuisineRest;
        } else if (cuisineRest.equals(allList)) {
            result = nameRest;
        } else {
            for (Restaurant restaurant : nameRest) {
                if (cuisineRest.contains(restaurant)) {
                    result.add(restaurant);
                }
            }
        }
        if (request.getOrder_by() != null) {
            if (request.getOrder_by().equals("avg_delivery"))
                result.sort(Comparator.comparing(Restaurant::getAvg_delivery));
            else if (request.getOrder_by().equals("min_amount"))
                result.sort(Comparator.comparing(Restaurant::getMin_amount));
        }
        return result;
    }

    @GetMapping("/orders/pending")
    public ResponseEntity<?> getPendingOrders() {
        try {
            List<Order> pendingOrders = restaurantService.findPendingOrdersForRestaurant(UserInfo.restaurant_id);
            return ResponseEntity.ok(pendingOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bekleyen siparişler alınırken bir hata oluştu, Serverhatası ");
        }
    }

    @GetMapping("/orders/accepted")
    public ResponseEntity<?> getAcceptedOrders() {
        try {
            List<Order> acceptedOrders = restaurantService.findAcceptedOrdersForRestaurant(UserInfo.restaurant_id);
            return ResponseEntity.ok(acceptedOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kabul edilmiş siparişler alınırken bir hata oluştu.");
        }
    }



    static class ConfirmOrderRequest {
        private Integer restaurantId;
        private Integer orderId;
        public Integer getRestaurantId() { return restaurantId; }
        public void setRestaurantId(Integer restaurantId) { this.restaurantId = restaurantId; }
        public Integer getOrderId() { return orderId; }
        public void setOrderId(Integer orderId) { this.orderId = orderId; }
    }
    @PostMapping("/orders/confirm")

    public ResponseEntity<?> confirmOrder(@RequestBody ConfirmOrderRequest request) {
        try {
            Order confirmedOrder = restaurantService.confirmOrder(request.getOrderId(), UserInfo.restaurant_id);
            return ResponseEntity.ok(confirmedOrder);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sipariş onayı sırasında bir server hatası oluştu.");
        }
    }

    @PostMapping("/orders/reject")

    public ResponseEntity<?> rejectOrder(@RequestBody ConfirmOrderRequest request) {
        try {
            Order rejectedOrder = restaurantService.rejectOrder(request.getOrderId(), UserInfo.restaurant_id);
            return ResponseEntity.ok(rejectedOrder);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sipariş reddi sırasında bir hata oluştu.");
        }
    }
}

