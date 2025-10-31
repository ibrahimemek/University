package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    List<Order> findByCourierIdAndOrderStatus(int courierId, OrderStatus status);
    List<Order> findByCourierIdAndOrderStatusIn(int courierId, List<OrderStatus> statuses);
    List<Order> findByRestaurantIdAndOrderStatus(Integer restaurantId, OrderStatus status);
    List<Order> findByOrderStatus(OrderStatus status);
    List<Order> findByOrderStatusNot(OrderStatus status);
    List<Order> findByOrderStatusIn(List<OrderStatus> statuses);
    @Query("SELECT o FROM Order o WHERE o.orderStatus = 'PENDING_ASSIGNMENT' OR (o.orderStatus = 'REJECTED_BY_COURIER' AND (o.lastRejectedCourierId IS NULL OR o.lastRejectedCourierId <> :courierId))")
    List<Order> findOrdersAvailableForCourier(@Param("courierId") Integer courierId);
    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus);

    @Query("SELECT o FROM Order o WHERE o.customerId = :customer_id AND o.orderStatus NOT IN ('DELIVERED', 'CANCELLED')")
    List<Order> findByCustomerIdAndOrderStatusNotDeliveredOrCancelled(@Param("customer_id") Integer customer_id);

    @Query("SELECT o FROM Order o WHERE o.customerId = :customer_id AND o.orderStatus IN ('DELIVERED')")
    List<Order> findByCustomerIdAndOrderStatusDelivered(@Param("customer_id") Integer customer_id);
}