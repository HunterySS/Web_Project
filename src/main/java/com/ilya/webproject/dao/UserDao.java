package com.ilya.webproject.dao;

import com.ilya.webproject.model.User;
import java.util.Optional;
import java.util.List;

public interface UserDao {
    boolean save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAll();
    boolean update(User user);
    boolean deleteById(Long id);
}