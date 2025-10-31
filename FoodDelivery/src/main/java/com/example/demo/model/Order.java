package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "Orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    private int order_id;
    @Column(nullable = false)
    private int customerId;
    @Column(nullable = false)
    private int restaurantId;

    @Column(nullable = false)
    private String deliveryAddress;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING_RESTAURANT_CONFIRMATION;
    private Integer courierId;
    private Integer lastRejectedCourierId;

    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime pickedUpAt; // tahmini varış saati falan göstermiyom. alındı dicem sadece
    private LocalDateTime deliveredAt;
    @Column(name = "total_amount")
    private Double totalPrice;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();
    public Order(){};
    public Order(int customerId, int restaurantId, String deliveryAddress, Double totalPrice) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        createdAt = LocalDateTime.now();
    }
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }
    public int getId() {
        return order_id;
    }

    public Integer getCourierId() {
        return courierId;
    }
    public void setCourierId(Integer id){
        courierId = id;
    }
    /*public void RestaurantRejectOrder(){ // ibo bu fonksiyonu sen bi daha bi düşün kanka şimdi sipariş oluşturulmazsa burada statusu değiştirmek mantıklı mı bilemedim
        orderStatus = OrderStatus.CANCELLED;
    }
    public void RestaurantAcceptOrder(){
        orderStatus = OrderStatus.PENDING_ASSIGNMENT;
        acceptedAt = LocalDateTime.now();
        // buradan sonra frontendde preparing aşamasına geçebilir
    }
    public void CourierAcceptOrder(Courier courier){
        orderStatus = OrderStatus.ACCEPTED_BY_COURIER;
        courier_id = courier.get_courier_id();
    }
    public void CourierRejectOrder(Courier courier, Order order, ){
        orderStatus = OrderStatus.REJECTED_BY_COURIER;
    }*/

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public LocalDateTime getPickedUpAt() {
        return pickedUpAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getPrice(){return totalPrice;}
    public void setPrice(Double totalPrice){ this.totalPrice = totalPrice;}
    public void setOrderStatus(OrderStatus status){orderStatus = status;}
    public void setAcceptedAt( LocalDateTime time ){acceptedAt = time;}
    public void setPickedUpAt( LocalDateTime time ){pickedUpAt = time;}
    public void setDeliveredAt( LocalDateTime time ){deliveredAt = time;}
    public OrderStatus getOrderStatus(){return orderStatus;}
    public int getRestaurantId(){return restaurantId;}
    public Integer getLastRejectedCourierId() { return lastRejectedCourierId; }
    public void setLastRejectedCourierId(Integer lastRejectedCourierId) { this.lastRejectedCourierId = lastRejectedCourierId; }

}
