package com.example.demo.repository;

import com.example.demo.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepo extends JpaRepository<Credit, Integer> {
    @Query("SELECT c FROM Credit c WHERE c.customer.customer_id = :customer_id")
    List<Credit> findByCustomerId(@Param("customer_id") Integer customer_id);
}
