package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.Customer;
import com.example.demo.model.MenuItem;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.MenuItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController
{
    @Autowired
    MenuItemRepo menuItemRepo;

    @Autowired
    CustomerRepo customerRepo;

    Cart cart = new Cart();

    @PostMapping("/add")
    public boolean addToCart(@RequestBody MenuItem item)
    {
        Optional<MenuItem> optional = menuItemRepo.findById(item.getItem_id());
        if (optional.isEmpty()) return false;
        MenuItem menuItem = optional.get();
        cart.addToCart(menuItem);
        return true;
    }
    @GetMapping("/get")
    public List<MenuItem> getCartItems() {
        return cart.getItems();
    }

    @DeleteMapping("/remove")
    public boolean removeFromCart(@RequestBody MenuItem item) {
        Optional<MenuItem> optional = menuItemRepo.findById(item.getItem_id());
        if (optional.isEmpty()) return false;
        MenuItem menuItem = optional.get();
        return cart.removeFromCart(menuItem);
    }
    @DeleteMapping("/clear")
    public void clearCart() {
        cart.clearCart();

    }
    @PostMapping("/checkout")
    public ResponseEntity<String> checkOut()
    {
        if (UserInfo.customer_id == -1) return new ResponseEntity<>("You should log in before checkout", HttpStatus.FORBIDDEN);
        Optional<Customer> optional = customerRepo.findById(UserInfo.customer_id);
        if (optional.isEmpty() || optional.get().getAddress() == null) return new ResponseEntity<>("You should add address before checkout", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>("Checkout successful", HttpStatus.OK); // 200 OK status
    }
}
