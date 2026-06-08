package com.ilya.webproject.service.impl;

import com.ilya.webproject.dao.UserDao;
import com.ilya.webproject.dao.impl.UserDaoImpl;
import com.ilya.webproject.model.User;
import com.ilya.webproject.service.UserService;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    private static UserService userService;
    private static Long testUserId;

    @BeforeAll
    static void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    @Order(1)
    void register_ShouldReturnTrue_WhenUserIsValid() {
        User user = new User("testuser", "test@example.com", "password123");

        boolean result = userService.register(user);

        assertTrue(result);
        assertNotNull(user.getId());
        testUserId = user.getId();
    }

    @Test
    @Order(2)
    void register_ShouldReturnFalse_WhenUsernameIsTaken() {
        User user = new User("testuser", "newemail@example.com", "password123");

        boolean result = userService.register(user);

        assertFalse(result);
    }

    @Test
    @Order(3)
    void login_ShouldReturnUser_WhenCredentialsAreValid() {
        Optional<User> result = userService.login("testuser", "password123");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    @Order(4)
    void login_ShouldReturnEmpty_WhenPasswordIsInvalid() {
        Optional<User> result = userService.login("testuser", "wrongpassword");

        assertTrue(result.isEmpty());
    }

    @Test
    @Order(5)
    void login_ShouldReturnEmpty_WhenUserNotFound() {
        Optional<User> result = userService.login("nonexistent", "password");

        assertTrue(result.isEmpty());
    }

    @Test
    @Order(6)
    void isUsernameTaken_ShouldReturnTrue_WhenUserExists() {
        boolean result = userService.isUsernameTaken("testuser");

        assertTrue(result);
    }

    @Test
    @Order(7)
    void isUsernameTaken_ShouldReturnFalse_WhenUserDoesNotExist() {
        boolean result = userService.isUsernameTaken("nonexistent");

        assertFalse(result);
    }

    @Test
    @Order(8)
    void isEmailTaken_ShouldReturnTrue_WhenEmailExists() {
        boolean result = userService.isEmailTaken("test@example.com");

        assertTrue(result);
    }

    @Test
    @Order(9)
    void isEmailTaken_ShouldReturnFalse_WhenEmailDoesNotExist() {
        boolean result = userService.isEmailTaken("nonexistent@example.com");

        assertFalse(result);
    }

    @Test
    @Order(10)
    void findByUsername_ShouldReturnUser_WhenExists() {
        Optional<User> result = userService.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    @Order(11)
    void findById_ShouldReturnUser_WhenExists() {
        Optional<User> result = userService.findById(testUserId);

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    @Order(12)
    void findAllUsers_ShouldReturnList() {
        var users = userService.findAllUsers();

        assertNotNull(users);
        assertTrue(users.size() >= 1);
    }

    @Test
    @Order(13)
    void deleteUser_ShouldReturnTrue_WhenDeletionSucceeds() {
        boolean result = userService.deleteUser(testUserId);

        assertTrue(result);

        Optional<User> deleted = userService.findById(testUserId);
        assertTrue(deleted.isEmpty());
    }
}