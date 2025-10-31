package com.example.demo.controller;

import com.example.demo.model.MenuItem;
import com.example.demo.model.Restaurant;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.MenuItemRepo;
import com.example.demo.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/menuItem")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;



    @PostMapping("/create")
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem food) {
        MenuItem createdMenuItem = menuItemService.createMenuItem(UserInfo.restaurant_id, food.getName(), food.getDescription(), food.getPrice(), true, food.getKey());
        return new ResponseEntity<>(createdMenuItem, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete")
    public boolean deleteMenuItem(@RequestBody MenuItem food) {
        return menuItemService.deleteMenuItem(food.getItem_id());
    }
    @PostMapping("/update")
    public boolean updateMenuItem(@RequestBody MenuItem food) {
        return menuItemService.updateMenuItem(food.getItem_id(), food.getName(), food.getDescription(), food.getPrice(), true, food.getKey());
    }
    @GetMapping("/getList")
    public List<MenuItem> getMenuItems() {
        return menuItemService.getAllByRestaurantId();
    }
    @PostMapping("/getMenu")
    public List<MenuItem> getRestaurantMenu(@RequestBody Restaurant restaurant)
    {
        return menuItemService.getMenu(restaurant.getRestaurant_id());
    }

}


