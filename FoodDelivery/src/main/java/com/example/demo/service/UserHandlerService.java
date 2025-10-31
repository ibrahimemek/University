package com.example.demo.service;


import com.example.demo.model.UserHandler;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserHandlerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.*;

@Service
public class UserHandlerService
{
    @Autowired
    private UserHandlerRepo userRepo;
    
    public boolean register(String email, String password, String user_type)
    {
        int isApproved = 0;
        if (user_type.equals("customer")) isApproved = 1;
        UserHandler user = new UserHandler(email, password, user_type, isApproved);

        userRepo.save(user);
        UserInfo.user_id = user.getUser_id();
        UserInfo.user_type = user_type;
        UserInfo.email = email;
        return true;
    }
    public boolean loginUser(String email, String password, String user_type) {
        String sql = "SELECT * FROM USER_HANDLER WHERE email = ? AND password = ? AND user_type = ?";
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";  // Your Oracle DB URL
        String DB_USER = "sys as SYSDBA";                          // Your DB username
        String DB_PASSWORD = "Emek.11223";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, user_type);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            if (rs.getInt("approved") == 0)
                return false;
            int user_id = rs.getInt("user_id");
            UserInfo.user_id = user_id;
            UserInfo.user_type = user_type;
            UserInfo.email = email;
            if (user_type.equals("restaurant"))
            {
                sql = "SELECT * FROM Restaurant WHERE user_id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, user_id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    UserInfo.restaurant_id = resultSet.getInt("restaurant_id");
                    return true;
                } else {
                    return false;
                }



            }
            else if (user_type.equals("customer"))
            {
                sql = "SELECT * FROM Customer WHERE user_id = ?";
                PreparedStatement statement1 = conn.prepareStatement(sql);
                statement1.setInt(1, user_id);
                ResultSet res = statement1.executeQuery();
                if (res.next()) {
                    UserInfo.customer_id = res.getInt("customer_id");
                    return true;
                } else {
                    return false;
                }

            }
            else if (user_type.equals("courier"))
            {
                sql = "SELECT * FROM Courier WHERE user_id = ?";
                PreparedStatement statement2 = conn.prepareStatement(sql);
                statement2.setInt(1, user_id);
                ResultSet res1 = statement2.executeQuery();
                if (res1.next()) {
                    UserInfo.courier_id = res1.getInt("courier_id");
                    return true;
                } else {
                    return false;
                }

            }
            else if (user_type.equals("admin"))
            {
                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
