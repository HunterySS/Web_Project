package com.ilya.webproject.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void hashPassword_ShouldReturnHashedValue() {
        String plainPassword = "mySecret123";

        String hashed = PasswordUtil.hashPassword(plainPassword);

        assertNotNull(hashed);
        assertNotEquals(plainPassword, hashed);
        assertTrue(hashed.startsWith("$2a$"));
    }

    @Test
    void checkPassword_ShouldReturnTrue_WhenPasswordMatches() {
        String plainPassword = "mySecret123";
        String hashed = PasswordUtil.hashPassword(plainPassword);

        boolean result = PasswordUtil.checkPassword(plainPassword, hashed);

        assertTrue(result);
    }

    @Test
    void checkPassword_ShouldReturnFalse_WhenPasswordDoesNotMatch() {
        String plainPassword = "mySecret123";
        String hashed = PasswordUtil.hashPassword(plainPassword);

        boolean result = PasswordUtil.checkPassword("wrongPassword", hashed);

        assertFalse(result);
    }
}