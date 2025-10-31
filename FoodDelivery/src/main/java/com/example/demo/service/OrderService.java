package com.example.demo.service;
import com.example.demo.model.*;
import com.example.demo.repository.MenuItemRepo;
import com.example.demo.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Service
public class OrderService {
    private final OrderRepo orderRepository;
    private final CourierService courierService;
    private final MenuItemRepo menuItemRepository;

    @Autowired
    public OrderService(OrderRepo orderRepository, CourierService courierService, MenuItemRepo menuItemRepository) {
        this.orderRepository = orderRepository;
        this.courierService = courierService;
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        if (orderRequest.getItems().isEmpty()) return null;
        Order theorder = new Order(UserInfo.customer_id,menuItemRepository.findById(orderRequest.getItems().get(0).getMenuItemId()).get().getRestaurant_id(), orderRequest.getDeliveryAddress(),0.0);
        double calculatedtotal = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderRequest.CartItem cartItem : orderRequest.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(cartItem.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Sipariş edilen ürün bulunamadı Id: " + cartItem.getMenuItemId()));
            OrderItem orderItem = new OrderItem(
                    menuItem.getItem_id(),
                    cartItem.getQuantity(),
                    menuItem.getPrice().doubleValue()
            );
            orderItems.add(orderItem);
            calculatedtotal += menuItem.getPrice().doubleValue() * cartItem.getQuantity();

        }
        theorder.setPrice(calculatedtotal);
        orderItems.forEach(theorder::addItem);
        orderRepository.save(theorder);
        return theorder;
    }
    @Transactional
    public Order confirmOrderByRestaurant(Integer orderId, Integer restaurantId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("onaylanacak sipariş bulunamadı" + orderId));
        if (order.getRestaurantId() != restaurantId) {
            throw new SecurityException("Bu siparişi reddetme yetkiniz yok.");
        }
        order.setOrderStatus(OrderStatus.PENDING_ASSIGNMENT);
        Order confirmedOrder = orderRepository.save(order);
        //attemptAssignCourier(confirmedOrder);
        return confirmedOrder;
    }

    @Transactional
    public Order rejectOrderByRestaurant(Integer orderId, Integer restaurantId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Reddedilecek sipariş bulunamadı ID: " + orderId));

        if (order.getRestaurantId() != restaurantId) {
            throw new SecurityException("Bu siparişi reddetme yetkiniz yok.");
        }

        if (order.getOrderStatus() != OrderStatus.PENDING_RESTAURANT_CONFIRMATION) {
            throw new IllegalStateException("Sipariş reddedilmek için uygun durumda değil");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Transactional
    public void attemptAssignCourier(Order order) {
        Courier availableCourier = courierService.findAvailableCourier();
        if (availableCourier == null) {
            System.out.println("Uygun kurye bulunamadı, sipariş sıraya alındı.");
            return;
        }
        assignOrderToCourierInternal(order, availableCourier);
    }


    private void assignOrderToCourierInternal(Order order, Courier courier) {
        Order currentOrder = orderRepository.findById(order.getId()).orElseThrow(() -> new RuntimeException("atama sırasında sipariş bulunamadı"));
        if (currentOrder.getCourierId() != null || currentOrder.getOrderStatus() != OrderStatus.PENDING_ASSIGNMENT) {
            throw new IllegalStateException("sipariş atama için uygun durumda değil çünkü atanmış");
        }
        currentOrder.setCourierId(courier.getCourierId());
        currentOrder.setOrderStatus(OrderStatus.ASSIGNED);
        orderRepository.save(currentOrder);
        courierService.updateCourierStatus(courier.getCourierId(), Courier.AvailabilityStatus.BUSY);
    }
    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(int orderId) { // kullanmıyorum ama kullanılabilir otomatik ekledi zaten kütüphane optional order ve içi null olabilir demek
        return orderRepository.findById(orderId);
    }
    @Transactional(readOnly = true)
    public OrderStatus getOrderStatus(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("sipariş bulunamadı"));
        return order.getOrderStatus();
    }

    @Transactional
    public Order cancelOrder(int orderId, int customerId) { //
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    return new RuntimeException("sipariş bulunamadı.");
                });

        //Siparişin müşteriye ait olup olmadığını kontrol et
        // Order modelinde customerId alanı kontrol eklenmeli
        // iptal edilebilirlik kontrolü hangi durumlarda iptal edilemez? buna bak
        List<OrderStatus> nonCancelableStatuses = Arrays.asList(
                OrderStatus.PICKED_UP,
                OrderStatus.DELIVERED,
                OrderStatus.CANCELLED
        );
        if (nonCancelableStatuses.contains(order.getOrderStatus())) {
            throw new IllegalStateException("Sipariş şu anki durumunda (" + order.getOrderStatus() + ") iptal edilemez.");
        }
        Integer assignedCourierId = order.getCourierId();
        if (assignedCourierId != null) {
                courierService.updateCourierStatus(assignedCourierId, Courier.AvailabilityStatus.AVAILABLE); // burada ben sana atmak için acele etmesem try catch le kuryeyi kontrol edicektim
            order.setCourierId(null);
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getNonCancelledOrders() {
        return orderRepository.findByOrderStatusNot(OrderStatus.CANCELLED);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersForCourierAssignment() {
        return orderRepository.findByOrderStatusIn(List.of(OrderStatus.PENDING_ASSIGNMENT, OrderStatus.REJECTED_BY_COURIER));
    }

    @Transactional
    public Order acceptOrder(int orderId, int courierId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));
        if (order.getOrderStatus() != OrderStatus.PENDING_ASSIGNMENT && order.getOrderStatus() != OrderStatus.REJECTED_BY_COURIER) {
            throw new IllegalStateException("Sipariş şu an atanamaz.");
        }
        order.setOrderStatus(OrderStatus.ACCEPTED_BY_COURIER);
        order.setCourierId(courierId);
        order.setLastRejectedCourierId(null);
        return orderRepository.save(order);
    }

    @Transactional
    public Order rejectOrder(int orderId, int courierId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));
        order.setOrderStatus(OrderStatus.REJECTED_BY_COURIER);
        order.setCourierId(null);
        order.setLastRejectedCourierId(courierId);
        return orderRepository.save(order);
    }
    public List<Order> findOrdersAvailableForCourier() {
        return orderRepository.findOrdersByOrderStatus(OrderStatus.PENDING_ASSIGNMENT);
    }

}