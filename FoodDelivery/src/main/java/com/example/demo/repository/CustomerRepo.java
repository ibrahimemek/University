package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>
{
    @Query("SELECT c FROM Customer c WHERE c.user_id = :user_id")
    List<Customer> findCustomerByUserId(@Param("user_id") Integer user_id);
}
