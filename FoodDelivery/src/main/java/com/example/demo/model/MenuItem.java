package com.example.demo.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Entity
public class MenuItem
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_sequence")
    @SequenceGenerator(name = "menu_item_sequence", sequenceName = "menu_item_sequence", allocationSize = 1)
    private int item_id;

    // Corresponds to `id` in the table
    private int restaurant_id;  // Corresponds to `restaurant_id` in the table

     // Specify the foreign key column
    private String name;           // Corresponds to `name` in the table
    private String description;    // Corresponds to `description` in the table
    private BigDecimal price;      // Corresponds to `price` in the table
    private boolean available;
    private String key;



    public MenuItem(){};
    public MenuItem(Integer restaurant_id, String name, String description, BigDecimal price, boolean available, String key) {
        this.restaurant_id = restaurant_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.key = key;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}