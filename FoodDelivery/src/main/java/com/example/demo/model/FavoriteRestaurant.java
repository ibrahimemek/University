package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class FavoriteRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_restaurant_sequence")
    @SequenceGenerator(name = "favorite_restaurant_sequence", sequenceName = "favorite_restaurant_sequence", allocationSize = 1)
    private int id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private int restaurantId;

    public FavoriteRestaurant() {
    }

    public FavoriteRestaurant(Customer customer, int restaurantId) {
        this.customer = customer;
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}