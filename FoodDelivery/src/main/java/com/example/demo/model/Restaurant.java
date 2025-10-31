package com.example.demo.model;

import jakarta.persistence.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Restaurant
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_sequence")
    @SequenceGenerator(name = "restaurant_sequence", sequenceName = "restaurant_sequence", allocationSize = 1)
    private int restaurant_id;
    private int user_id;
    private String contact;
    private String address;
    private String name;
    private int avg_delivery;
    private String business_hours;
    private String status;
    private String cuisine;
    private int min_amount;
    private float rating;

    public Restaurant(){};

    public Restaurant(int user_id, String contact, String address,
                      String name, int avg_delivery, String business_hours,
                      String status, String cuisine, int min_amount) {
        this.user_id = user_id;
        this.contact = contact;
        this.address = address;
        this.name = name;
        this.avg_delivery = avg_delivery;
        this.business_hours = business_hours;
        this.status = status;
        this.cuisine = cuisine;
        this.min_amount = min_amount;
    }
    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvg_delivery() {
        return avg_delivery;
    }

    public void setAvg_delivery(int avg_delivery) {
        this.avg_delivery = avg_delivery;
    }

    public String getBusiness_hours() {
        return business_hours;
    }

    public void setBusiness_hours(String business_hours) {
        this.business_hours = business_hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(int min_amount) {
        this.min_amount = min_amount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
