package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/address")
public class AddressController
{
    @Autowired
    AddressService service;

    @PostMapping("/add")
    public Address addAddress(@RequestBody Address address)
    {
        return service.addAddress(address.getAddress());
    }
    @GetMapping("/getAll")
    public List<Address> getAll()
    {
        return service.allAddresses();
    }
    @PostMapping("/delete")
    public boolean deleteAddress(@RequestBody Address address)
    {
        return service.deleteAddress(address.getAddressId());
    }
    @PostMapping("/update")
    public Address updateAddress(@RequestBody Address address)
    {
        return service.updateAddress(address.getAddressId(), address.getAddress());
    }
}
