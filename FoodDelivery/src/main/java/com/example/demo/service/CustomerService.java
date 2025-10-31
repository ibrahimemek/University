package com.example.demo.service;


import com.example.demo.model.*;
import com.example.demo.repository.AddressRepo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService
{
    @Autowired
    private CustomerRepo repo;

    @Autowired
    private OrderRepo orderRepo;



    public Customer createCustomer(int user_id)
    {
        Customer customer = new Customer(user_id);
        repo.save(customer);
        return customer;
    }
    public boolean addAddress(Integer item_id, String address)
    {
        Optional<Customer> optional = repo.findById(item_id);
        if (optional.isEmpty()) return false;
        Customer customer = optional.get();
        customer.setAddress(address);
        repo.save(customer);
        return true;
    }
    public List<Order> getActiveOrders(Integer customer_id)
    {
        return orderRepo.findByCustomerIdAndOrderStatusNotDeliveredOrCancelled(customer_id);
    }
    public List<Order> getDeliveredOrders(Integer customer_id)
    {
        return orderRepo.findByCustomerIdAndOrderStatusDelivered(customer_id);
    }

    public Customer getProfile()
    {
        Optional<Customer> opt = repo.findById(UserInfo.customer_id);
        if (opt.isEmpty()) return null;
        return opt.get();
    }



}