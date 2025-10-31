package com.example.demo.model;

import jakarta.persistence.*; // veya javax.persistence.* (Spring Boot sürümüne göre)
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "COURIER")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courier_sequence")
    @SequenceGenerator(name = "courier_sequence", sequenceName = "courier_sequence", allocationSize = 1)
    @Column(name = "courier_id")
    private int courierId;
    
    @Column(name = "user_id")
    private int user_id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;
    
    @Column(name = "email")
    private String email;

    public enum AvailabilityStatus {
        AVAILABLE,
        BUSY,
        OFFLINE // needs admin verification
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;

    public Courier() {}

    public Courier(int id, String phoneNumber, String email, String name) {
        this.user_id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.name = name;
    }

    public String getPhoneNumber(){return phoneNumber;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public void setEmail(String email){this.email = email;}

    public String getEmail(){return email;}
    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int id) {
        this.courierId = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }
    public String getName(){return name;}
    // Kuryenin anlık konumu (İleri seviye özellikler için eklenebilir)
    // private Double latitude;
    // private Double longitude;



    // Müsaitlik durumlarını yönetmek için Enum

}