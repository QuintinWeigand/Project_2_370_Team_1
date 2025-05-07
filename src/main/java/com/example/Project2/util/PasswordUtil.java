package com.example.Project2.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Utility class for secure password operations.
 */
public class PasswordUtil {

    /**
     * Hash a password using BCrypt.
     * 
     * @param plainTextPassword The plain text password to hash
     * @return The hashed password
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }
    
    /**
     * Verify a password against a stored hash.
     * 
     * @param plainTextPassword The plain text password to check
     * @param hashedPassword The stored hashed password
     * @return True if password matches the hash, false otherwise
     */
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}