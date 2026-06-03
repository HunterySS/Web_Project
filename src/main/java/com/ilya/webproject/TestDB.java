package com.ilya.webproject;

import com.ilya.webproject.dao.UserDao;
import com.ilya.webproject.dao.impl.UserDaoImpl;
import com.ilya.webproject.model.User;
import com.ilya.webproject.util.DatabaseConnectionPool;

import java.sql.Connection;
import java.util.Optional;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnectionPool.getConnection();
            System.out.println("Database connected successfully!");
            conn.close();

            UserDao userDao = new UserDaoImpl();

            Optional<User> user = userDao.findByUsername("admin");
            if (user.isPresent()) {
                System.out.println("User found: " + user.get());
            } else {
                System.out.println("Admin user not found");
            }

            User newUser = new User("testuser", "test@example.com", "password123");
            if (userDao.save(newUser)) {
                System.out.println("New user saved with id: " + newUser.getId());
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnectionPool.closePool();
        }
    }
}
