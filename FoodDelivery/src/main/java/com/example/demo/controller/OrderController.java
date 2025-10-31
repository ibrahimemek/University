package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.OrderRequest;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.UserInfo;
import com.example.demo.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {



    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Order createdOrder = orderService.createOrder(orderRequest);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Sipariş oluşturulamadı: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sipariş oluşturulurken sunucu hatası oluştu");
        }
    }

    @GetMapping("/details")
    public ResponseEntity<?> getOrderDetails(@RequestParam int orderId) {
        try {
            Optional<Order> orderOpt = orderService.getOrderById(orderId);
            return orderOpt
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

                    });
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sunucu hatası oluştu.");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getOrderStatus(@RequestParam int orderId) {
        // TODO: Güvenlik kontrolü
        try {
            OrderStatus status = orderService.getOrderStatus(orderId);
            return ResponseEntity.ok().body(new Object() {
                public final OrderStatus orderStatus = status;
            });
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("sipariş bulunamadı")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            // Diğer RuntimeException'lar için
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sipariş durumu alınırken bir hata oluştu.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sunucu hatası oluştu.");
        }
    }

    static class CancelOrderRequest {
        private int orderId;
        private int customerId;

        public int getOrderId() { return orderId; }
        public void setOrderId(int orderId) { this.orderId = orderId; }
        public int getCustomerId() { return customerId; }
        public void setCustomerId(int customerId) { this.customerId = customerId; }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestBody Order order) {
        try {
            Order cancelledOrder = orderService.cancelOrder(order.getId(), UserInfo.customer_id);
            return ResponseEntity.ok(cancelledOrder);
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("sipariş bulunamadı")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sipariş iptal edilirken bir hata oluştu.");
            }
        }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveOrders() {
        try {
            List<Order> orders = orderService.getNonCancelledOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sunucu hatası oluştu.");
        }
    }

    
    @GetMapping("/available-for-courier")
    public ResponseEntity<?> getOrdersAvailableForCourier() {
        try {
            List<Order> availableOrders = orderService.findOrdersAvailableForCourier();
            return ResponseEntity.ok(availableOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kuryeler için uygun siparişler alınırken bir hata oluştu.");
        }
    }
    

    
}
