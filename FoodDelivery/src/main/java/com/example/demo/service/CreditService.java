package com.example.demo.service;

import com.example.demo.model.Credit;
import com.example.demo.model.Customer;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.CreditRepo;
import com.example.demo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditService {
    @Autowired
    private CreditRepo creditRepo;

    @Autowired
    private CustomerRepo customerRepo;

    public Credit addCredit(Credit credit) {
        Optional<Customer> opt = customerRepo.findById(UserInfo.customer_id);
        if (opt.isEmpty()) return null;

        Credit newCard = new Credit(opt.get(), credit.getCard_owner(), credit.getCard_number(), credit.getExp_date(), credit.getCvv());
        return creditRepo.save(newCard);
    }

    public boolean deleteCredit(int card_id) {
        creditRepo.deleteById(card_id);
        return true;
    }

    public List<Credit> allCards() {
        return creditRepo.findByCustomerId(UserInfo.customer_id);
    }

    public Credit updateCredit(Credit updated) {
        Optional<Credit> opt = creditRepo.findById(updated.getCard_id());
        if (opt.isEmpty()) return null;

        Credit card = opt.get();
        card.setCard_owner(updated.getCard_owner());
        card.setCard_number(updated.getCard_number());
        card.setExp_date(updated.getExp_date());
        card.setCvv(updated.getCvv());

        return creditRepo.save(card);
    }
}
