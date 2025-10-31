package com.example.demo.repository;

import com.example.demo.model.Address;
import com.example.demo.model.Courier;
import com.example.demo.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.customer.customer_id = :customer_id")
    List<Address> findByCustomerId(@Param("customer_id") Integer customer_id);
}
