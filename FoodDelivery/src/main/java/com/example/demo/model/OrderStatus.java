package com.example.demo.model;

public enum OrderStatus {
    PENDING_RESTAURANT_CONFIRMATION,
    PENDING_ASSIGNMENT,
    ASSIGNED,
    ACCEPTED_BY_COURIER,
    REJECTED_BY_COURIER,
    PICKED_UP,
    DELIVERED,
    CANCELLED
}