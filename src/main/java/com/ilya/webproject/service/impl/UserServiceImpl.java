package com.ilya.webproject.service.impl;

import com.ilya.webproject.dao.UserDao;
import com.ilya.webproject.dao.impl.UserDaoImpl;
import com.ilya.webproject.model.User;
import com.ilya.webproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean register(User user) {
        logger.info("Registering new user: {}", user.getUsername());

        if (isUsernameTaken(user.getUsername())) {
            logger.warn("Username {} is already taken", user.getUsername());
            return false;
        }

        if (isEmailTaken(user.getEmail())) {
            logger.warn("Email {} is already taken", user.getEmail());
            return false;
        }

        return userDao.save(user);
    }

    @Override
    public Optional<User> login(String username, String password) {
        logger.info("Login attempt for user: {}", username);

        Optional<User> userOpt = userDao.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                logger.info("Login successful for user: {}", username);
                return userOpt;
            } else {
                logger.warn("Invalid password for user: {}", username);
            }
        } else {
            logger.warn("User not found: {}", username);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        logger.info("Finding all users");
        return userDao.findAll();
    }

    @Override
    public boolean updateUser(User user) {
        logger.info("Updating user: {}", user.getUsername());
        return userDao.update(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        return userDao.deleteById(id);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userDao.findByUsername(username).isPresent();
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userDao.findByEmail(email).isPresent();
    }
}