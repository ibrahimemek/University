package com.example.demo.controller;

import com.example.demo.model.Restaurant;
import com.example.demo.model.UserHandler;
import com.example.demo.model.UserHandler;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/handle")
public class UserHandlerController {

    @Autowired
    private UserHandlerService userHandlerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserHandler user) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (user.getEmail() == null || !user.getEmail().matches(emailRegex)) return new
                ResponseEntity<>("Invalid email", HttpStatus.FORBIDDEN);
        if (user.getPassword().length() < 4) return new
                ResponseEntity<>("Your password should be more than 3 characters", HttpStatus.FORBIDDEN);
        boolean registeredUser = userHandlerService.register(user.getEmail(), user.getPassword(), user.getUser_type());
        if (!registeredUser) return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>("Successfully registered", HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody UserHandler user) {

        boolean registeredUser = userHandlerService.loginUser(user.getEmail(), user.getPassword(), user.getUser_type());
        if (!registeredUser) return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>("Successfully logged in", HttpStatus.CREATED);
    }
    @PostMapping("/logout")
    public void logOut()
    {

    }

}
