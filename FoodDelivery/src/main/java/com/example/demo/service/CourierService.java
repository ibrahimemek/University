package com.example.demo.service;

import com.example.demo.model.Courier;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.CourierRepo;
import com.example.demo.repository.OrderRepo;
import com.example.demo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

@Service
public class CourierService {
    private final CourierRepo courierRepository;
    private final OrderRepo orderRepository;

    @Autowired
    public CourierService(CourierRepo courierRepository, OrderRepo orderRepository) {
        this.courierRepository = courierRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Courier createCourier(int id , String phoneNumber, String email,String name) throws IllegalArgumentException{
        Courier courier = new Courier(id, phoneNumber, email,name);
        courierRepository.findByPhoneNumber(courier.getPhoneNumber())
                .ifPresent(existingCourier -> {
                    throw new IllegalArgumentException("bu numara zaten kayıtlı: " + courier.getPhoneNumber());
                });

        if (courier.getEmail() != null && !courier.getEmail().isEmpty()) {
            courierRepository.findByEmail(courier.getEmail())
                    .ifPresent(existingCourier -> {
                        throw new IllegalArgumentException("Bu eposta zaten kayıtlı: " + courier.getEmail());
                    });// kurye için ikisi de önemli
        }
    
        Courier savedCourier = courierRepository.save(courier);
        UserInfo.courier_id = savedCourier.getCourierId();
        
        return savedCourier;
    }

    @Transactional(readOnly = true)
    public Courier findAvailableCourier(){
        Courier courier = courierRepository.findFirstByAvailabilityStatusOrderByCourierIdAsc(Courier.AvailabilityStatus.AVAILABLE).
                orElseThrow(()-> new IllegalArgumentException("uygun kurye bulunamadı"));
        return courier;
    }

    @Transactional
    public Courier updateCourierStatus(int courierId , Courier.AvailabilityStatus status){
        
        System.out.println(courierId);
        List<Courier> optional = courierRepository.findCourierByCourierId(courierId);
        System.out.println("2");
        if(optional.isEmpty()) return null;
        System.out.println("3");
        Courier courier = optional.get(0);
        System.out.println("4");
        courier.setAvailabilityStatus(status);
        System.out.println("5");
        return courierRepository.save(courier);
        }

    @Transactional
    public Order acceptOrder(int orderId, int courierId) {
        Order order = findOrderAndVerifyCourier(orderId, courierId, OrderStatus.PENDING_ASSIGNMENT);
        order.setOrderStatus(OrderStatus.ACCEPTED_BY_COURIER);
        order.setCourierId(courierId);
        order.setAcceptedAt(LocalDateTime.now());
        updateCourierStatus(courierId, Courier.AvailabilityStatus.BUSY); // gerekli değil aslında da ben yapıyom bidaha
        return orderRepository.save(order);
    }

    @Transactional
    public Order rejectOrder(int orderId, int courierId) {
        Order order = findOrderAndVerifyCourier(orderId, courierId, OrderStatus.ASSIGNED);
        order.setOrderStatus(OrderStatus.PENDING_ASSIGNMENT);
        order.setCourierId(null);
        order.setLastRejectedCourierId(courierId);
        Order savedOrder = orderRepository.save(order);
        updateCourierStatus(courierId, Courier.AvailabilityStatus.AVAILABLE);
        return savedOrder;
    }

    @Transactional
    public Order markOrderAsPickedUp(int orderId, int courierId) {
        Order order = findOrderAndVerifyCourier(orderId, courierId, OrderStatus.ACCEPTED_BY_COURIER);

        order.setOrderStatus(OrderStatus.PICKED_UP);
        order.setPickedUpAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
    @Transactional
    public Order markOrderAsDelivered(int orderId, int courierId) {
        Order order = findOrderAndVerifyCourier(orderId, courierId, OrderStatus.PICKED_UP);
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDeliveredAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        updateCourierStatus(courierId, Courier.AvailabilityStatus.AVAILABLE);
        return savedOrder;
    }

    private Order findOrderAndVerifyCourier(int orderId, int courierId, OrderStatus expectedStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    return new RuntimeException("sipariş bulunamadı");
                });

        return order;
    }
    @Transactional(readOnly = true)
    public List<Order> findAssignedOrdersForCourier(int courierId) {
        return orderRepository.findByCourierIdAndOrderStatus(courierId, OrderStatus.ACCEPTED_BY_COURIER);
    }
    @Transactional(readOnly = true)
    public List<Order> findPickedOrdersForCourier(int courierId) {
        return orderRepository.findByCourierIdAndOrderStatus(courierId, OrderStatus.PICKED_UP);
    }

    @Transactional(readOnly = true) // forntendde buton konularrsa aktif siparişleri çekeriz
    public List<Order> findActiveOrdersForCourier(int courierId) {
        List<OrderStatus> activeStatuses = Arrays.asList(OrderStatus.ACCEPTED_BY_COURIER, OrderStatus.PICKED_UP);
        return orderRepository.findByCourierIdAndOrderStatusIn(courierId, activeStatuses);
    }
}