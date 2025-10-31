package com.example.demo.service;

import com.example.demo.model.MenuItem;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.MenuItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService
{
    @Autowired
    private MenuItemRepo menuItemRepo;



    public MenuItem createMenuItem(Integer restaurant_id, String name, String description, BigDecimal price, boolean available, String key) {
        MenuItem menuItem = new MenuItem(restaurant_id, name, description, price, available, key);
        menuItemRepo.save(menuItem);
        return menuItem; // Assuming foodRepo correctly handles ID management
    }
    public boolean deleteMenuItem(Integer item_id)
    {
        try
        {
            menuItemRepo.deleteById(item_id);
            return true;

        }catch (Exception e){return false;}
    }
    public boolean updateMenuItem(Integer item_id, String name, String description, BigDecimal price, boolean available, String key)
    {
        Optional<MenuItem> optional = menuItemRepo.findById(item_id);
        if (optional.isEmpty()) return false;
        MenuItem item = optional.get();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setAvailable(available);
        item.setKey(key);
        menuItemRepo.save(item);
        return true;

    }
    public List<MenuItem> getAllByRestaurantId()
    {
        return menuItemRepo.findByRestaurantId(UserInfo.restaurant_id);
    }
    public void addToCart(Integer item_id)
    {
        Optional<MenuItem> optional = menuItemRepo.findById(item_id);
        if (optional.isEmpty()) return;
        MenuItem item = optional.get();

    }
    public List<MenuItem> getMenu(int restaurant_id)
    {
        return menuItemRepo.findByRestaurantId(restaurant_id);
    }


}
