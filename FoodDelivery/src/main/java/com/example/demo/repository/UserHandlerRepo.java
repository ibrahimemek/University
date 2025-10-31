package com.example.demo.repository;

import com.example.demo.model.UserHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHandlerRepo extends JpaRepository<UserHandler, Integer>
{
    List<UserHandler> getUserHandlersByApproved(int approved);

    UserHandler findByEmail(String email);
}
