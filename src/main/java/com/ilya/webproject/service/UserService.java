package com.ilya.webproject.service;

import com.ilya.webproject.model.User;
import java.util.Optional;
import java.util.List;

public interface UserService {
    boolean register(User user);
    Optional<User> login(String username, String password);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAllUsers();
    boolean updateUser(User user);
    boolean deleteUser(Long id);
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
}