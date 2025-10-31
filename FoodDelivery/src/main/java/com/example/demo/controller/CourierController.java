package com.example.demo.controller;

import com.example.demo.model.Courier;
import com.example.demo.model.Order;
import com.example.demo.model.UserInfo;
import com.example.demo.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/couriers")
public class CourierController {
    @Autowired
    private CourierService courierService;


    @PostMapping("/create")
    public ResponseEntity<Courier> createCourier(@RequestBody Courier request) {
        try {
            Courier createdCourier = courierService.createCourier(UserInfo.user_id, request.getPhoneNumber(), UserInfo.email,request.getName());
            return new ResponseEntity<>(createdCourier, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    static class UpdateCourierStatusRequest { // senin yaptığın search request giib düşün bunları.
        private int courierId; // hangi kurye guncellencek
        private Courier.AvailabilityStatus status;

        public int getCourierId() { return courierId; }
        public void setCourierId(int courierId) { this.courierId = courierId; }
        public Courier.AvailabilityStatus getStatus() { return status; }
        public void setStatus(Courier.AvailabilityStatus status) { this.status = status; }
    }

    @PutMapping("/update/status")
    public ResponseEntity<Courier> updateCourierStatus(@RequestBody UpdateCourierStatusRequest request) {
        try {
            Courier updatedCourier = courierService.updateCourierStatus(UserInfo.courier_id, request.getStatus());
            return ResponseEntity.ok(updatedCourier);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/orders/assigned")
    public ResponseEntity<List<Order>> getAssignedOrders() {
        try {
            List<Order> orders = courierService.findAssignedOrdersForCourier(UserInfo.courier_id);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/orders/picked")
    public ResponseEntity<List<Order>> getPickedOrders() {
        try {
            List<Order> orders = courierService.findPickedOrdersForCourier(UserInfo.courier_id);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/orders/accept")
    public ResponseEntity<Order> acceptOrder(@RequestBody Order order) {
        try {
            Order updatedOrder = courierService.acceptOrder(order.getId(), UserInfo.courier_id);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/orders/reject")
    public ResponseEntity<Order> rejectOrder(@RequestBody Order order) {
        try {
            Order updatedOrder = courierService.rejectOrder(order.getId(), UserInfo.courier_id);
            return ResponseEntity.ok(updatedOrder);
        }catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/orders/pickup")
    public ResponseEntity<Order> markOrderAsPickedUp(@RequestBody Order order) {
        try {
            Order updatedOrder = courierService.markOrderAsPickedUp(order.getId(), UserInfo.courier_id);

            return ResponseEntity.ok(updatedOrder);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/orders/deliver")
    public ResponseEntity<Order> markOrderAsDelivered(@RequestBody Order order) {
        try {
            Order updatedOrder = courierService.markOrderAsDelivered(order.getId(), UserInfo.courier_id);
            return ResponseEntity.ok(updatedOrder);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}