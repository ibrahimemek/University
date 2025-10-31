package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rev_sequence")
    @SequenceGenerator(name = "rev_sequence", sequenceName = "rev_sequence", allocationSize = 1)
    private int review_id;
    
    @Column(nullable = false)
    private int customer_id;
    
    @Column(nullable = false)
    private int restaurant_id;
    
    @Column(nullable = false)
    private int order_id;
    
    @Column(nullable = false)
    private int rating;
    
    @Column(length = 1000)
    private String review_comment;

    @Column(nullable = false)
    private LocalDateTime created_at;

    public Review() {
        this.created_at = LocalDateTime.now();
    }

    public Review(int customer_id, int restaurant_id, int order_id, int rating, String comment) {
        this.customer_id = customer_id;
        this.restaurant_id = restaurant_id;
        this.order_id = order_id;
        this.rating = rating;
        this.review_comment = comment;
        this.created_at = LocalDateTime.now();
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview_comment() {
        return review_comment;
    }

    public void setReview_comment(String review_comment) {
        this.review_comment = review_comment;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
} 