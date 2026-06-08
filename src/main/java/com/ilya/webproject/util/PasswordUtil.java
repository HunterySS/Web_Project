package com.ilya.webproject.util;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordUtil {
    private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

    private PasswordUtil() {}

    public static String hashPassword(String plainPassword) {
        String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        logger.debug("Password hashed successfully");
        return hashed;
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        boolean isValid = BCrypt.checkpw(plainPassword, hashedPassword);
        logger.debug("Password check result: {}", isValid);
        return isValid;
    }
}