package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence", allocationSize = 1)
    private int card_id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String card_owner;
    private String card_number;
    private String exp_date;
    private String cvv;

    public Credit() {}

    public Credit(Customer customer, String card_owner, String card_number, String exp_date, String cvv) {
        this.customer = customer;
        this.card_owner = card_owner;
        this.card_number = card_number;
        this.exp_date = exp_date;
        this.cvv = cvv;
    }

    // Getter & Setter'lar
    public int getCard_id() { return card_id; }
    public void setCard_id(int card_id) { this.card_id = card_id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getCard_owner() { return card_owner; }
    public void setCard_owner(String card_owner) { this.card_owner = card_owner; }

    public String getCard_number() { return card_number; }
    public void setCard_number(String card_number) { this.card_number = card_number; }

    public String getExp_date() { return exp_date; }
    public void setExp_date(String exp_date) { this.exp_date = exp_date; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}
