package com.example.demo.controller;


import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.UserInfo;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController
{
    @Autowired
    private CustomerService service;


    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer()
    {
        Customer customer = service.createCustomer(UserInfo.user_id);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
    @PostMapping("/addAddress")
    public boolean addAddress(@RequestBody Customer customer)
    {
        return service.addAddress(UserInfo.customer_id, customer.getAddress());
    }

    @GetMapping("/getActiveOrders")
    public List<Order> getActiveOrders()
    {
        return service.getActiveOrders(UserInfo.customer_id);
    }

    @GetMapping("/getHistory")
    public List<Order> getHistory(){ return service.getDeliveredOrders(UserInfo.customer_id);}

    @GetMapping("/getProfile")
    public Customer getProfile()
    {
        return service.getProfile();
    }



}
