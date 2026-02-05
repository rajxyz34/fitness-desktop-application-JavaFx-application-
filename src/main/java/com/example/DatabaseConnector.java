package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/project_db";
    private static final String USER = "root";
    private static final String PASSWORD = "******";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Hash password using BCrypt
    //encrypting the password before storing in databses;
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt()); // Hash with salt
    }

    // Validate user during login
    public boolean validateUser(String username, String password) {
        String query = "SELECT password FROM users WHERE username=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                return BCrypt.checkpw(password, storedHashedPassword); // Check if password matches hashed password
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Register user with hashed password
    public boolean registerUser(String username, String password,int age,double height,double weight,String fname,String lname  ) {
        String hashedPassword = hashPassword(password); // Hash the password
        String query = "INSERT INTO users (username, password,Fname,Lname,Age,Weight,Height) VALUES (?, ?, ? ,?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, hashedPassword);
                stmt.setString(3, fname);
                stmt.setString(4, lname);
                stmt.setInt(5, age);
                stmt.setDouble(6, weight);
                stmt.setDouble(7, height);
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
