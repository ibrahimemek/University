package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.Customer;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.AddressRepo;
import com.example.demo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService
{
    @Autowired
    AddressRepo addressRepo;

    @Autowired
    CustomerRepo customerRepo;

    public Address addAddress(String address)
    {
        Optional<Customer> opt = customerRepo.findById(UserInfo.customer_id);
        if(opt.isEmpty()) return null;

        Address address1 = new Address(opt.get(), address);
        addressRepo.save(address1);
        return address1;
    }

    public boolean deleteAddress(int address_id)
    {
        addressRepo.deleteById(address_id);
        return true;
    }
    public List<Address> allAddresses()
    {
        return addressRepo.findByCustomerId(UserInfo.customer_id);
    }
    public Address updateAddress(int address_id, String newAddress)
    {
        Optional<Address> opt = addressRepo.findById(address_id);
        if (opt.isEmpty()) return null;
        opt.get().setAddress(newAddress);
        addressRepo.save(opt.get());
        return opt.get();
    }

}
