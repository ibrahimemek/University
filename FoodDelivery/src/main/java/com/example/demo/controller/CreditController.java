package com.example.demo.controller;

import com.example.demo.model.Credit;
import com.example.demo.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/card")
public class CreditController {
    @Autowired
    private CreditService service;

    @PostMapping("/add")
    public Credit addCredit(@RequestBody Credit credit) {
        return service.addCredit(credit);
    }

    @PostMapping("/delete")
    public boolean deleteCredit(@RequestBody Credit credit) {
        return service.deleteCredit(credit.getCard_id());
    }

    @PostMapping("/update")
    public Credit updateCredit(@RequestBody Credit credit) {
        return service.updateCredit(credit);
    }

    @GetMapping("/getAll")
    public List<Credit> getAll() {
        return service.allCards();
    }
}
