package com.example.demo.controller;


import com.example.demo.model.Customer;
import com.example.demo.model.MenuItem;
import com.example.demo.model.Restaurant;
import com.example.demo.model.UserHandler;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.MenuItemRepo;
import com.example.demo.repository.RestaurantRepo;
import com.example.demo.repository.UserHandlerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController
{
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    UserHandlerRepo userHandlerRepo;
    @Autowired
    RestaurantRepo restaurantRepo;
    @Autowired
    MenuItemRepo menuItemRepo;


    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody UserHandler user)
    {
        List<Customer> customers = customerRepo.findCustomerByUserId(user.getUser_id());
        if(!customers.isEmpty()) customerRepo.deleteById(customers.get(0).getCustomer_id());
        List<Restaurant> restaurants = restaurantRepo.findRestaurantByUserId(user.getUser_id());
        if(!restaurants.isEmpty())
        {
            List<MenuItem> items = menuItemRepo.findByRestaurantId(restaurants.get(0).getRestaurant_id());
            menuItemRepo.deleteAll(items);
            restaurantRepo.deleteById(restaurants.get(0).getRestaurant_id());
        }
        userHandlerRepo.deleteById(user.getUser_id());
        return true;
    }

    @PostMapping("/approve")
    public boolean approveUser(@RequestBody UserHandler user)
    {
        Optional<UserHandler> changed = userHandlerRepo.findById(user.getUser_id());
        if(changed.isPresent()) changed.get().setApproved(user.getApproved());
        else return false;
        userHandlerRepo.flush();
        return true;
    }

    @GetMapping("/getUnapproved")
    public List<UserHandler> getUnapproved()
    {
        return userHandlerRepo.getUserHandlersByApproved(0);
    }



    @GetMapping("/users")
    public List<UserHandler> getAll()
    {
        return userHandlerRepo.findAll();
    }

}
