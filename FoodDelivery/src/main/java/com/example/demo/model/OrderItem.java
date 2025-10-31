package com.example.demo.model; // veya com.yourpackage.model

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

// Getter'ı manuel eklemeye gerek yok, @Data sağlıyor olmalı
// import lombok.Getter;

@Entity
@Table(name = "order_items")
@Data // @Setter'ı da içerir ama manuel ekleyeceğiz
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_sequence")
    @SequenceGenerator(name = "order_item_sequence", sequenceName = "order_item_sequence", allocationSize = 1)
    @Column(name = "order_item_id")
    private int orderItemId;

    public OrderItem() {
    }

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "item_id", nullable = false)
    private int menuItemId;



    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_per_item", nullable = false)
    private Double pricePerItem;
    public OrderItem(int menuItemId,  int quantity, Double OrderPrice) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.pricePerItem = OrderPrice;
    }

    // *** MANUEL OLARAK SETTER EKLE ***
    public void setOrder(Order order) {
        this.order = order;
    }

    // ID getter (Lombok sağlıyor ama emin olmak için)
    public int getId() {
        return orderItemId;
    }

    // Order getter (Lombok sağlıyor ama emin olmak için)
    public Order getOrder(){
        return order;
    }

}