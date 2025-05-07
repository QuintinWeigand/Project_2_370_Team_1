package com.example.Project2.pages;

import com.example.Project2.model.User;
import com.example.Project2.services.SessionService;
import com.example.Project2.services.UserService;
import com.example.Project2.util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Registration page that allows new users to create accounts.
 */
public class Register {
    
    private static final Logger logger = LogManager.getLogger(Register.class);
    
    @Inject
    private AlertManager alertManager;
    
    @Inject
    private UserService userService;
    
    @Inject
    private SessionService sessionService;
    
    @InjectComponent
    private Form registerForm;
    
    @InjectComponent("username")
    private TextField usernameField;
    
    @InjectComponent("email")
    private TextField emailField;
    
    @InjectComponent("password")
    private PasswordField passwordField;
    
    @InjectComponent("confirmPassword")
    private PasswordField confirmPasswordField;
    
    @Property
    private String username;
    
    @Property
    private String email;
    
    @Property
    private String password;
    
    @Property
    private String confirmPassword;
    
    // Called when first visiting the registration page
    void onActivate() {
        // If already logged in, redirect to index
        if (sessionService.isLoggedIn()) {
            alertManager.info("You are already logged in as " + sessionService.getCurrentUsername());
        }
    }
    
    void onValidateFromRegisterForm() {
        if (username == null || username.trim().isEmpty()) {
            registerForm.recordError(usernameField, "Username is required");
            return;
        }
        
        if (email == null || email.trim().isEmpty()) {
            registerForm.recordError(emailField, "Email is required");
            return;
        }
        
        if (password == null || password.isEmpty()) {
            registerForm.recordError(passwordField, "Password is required");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            registerForm.recordError(confirmPasswordField, "Passwords do not match");
            return;
        }
        
        // Check if username or email already exists
        try {
            if (userService.getUserByUsername(username) != null) {
                registerForm.recordError(usernameField, "Username already exists");
                return;
            }
            
            if (userService.getUserByEmail(email) != null) {
                registerForm.recordError(emailField, "Email already exists");
                return;
            }
        } catch (Exception e) {
            registerForm.recordError("Error checking for existing user: " + e.getMessage());
            logger.error("Error during user registration validation", e);
        }
    }
    
    Object onSuccessFromRegisterForm() {
        try {
            // Create new user
            User user = new User(
                null,
                username,
                email,
                PasswordUtil.hashPassword(password),
                "USER" // Default role
            );
            
            userService.addUser(user);
            sessionService.login(user);
            
            alertManager.success("Registration successful! Welcome, " + username);
            logger.info("New user registered: {}", username);
            
            return Index.class;
        } catch (Exception e) {
            alertManager.error("Registration failed: " + e.getMessage());
            logger.error("Error during user registration", e);
            return null;
        }
    }
    
    void onFailureFromRegisterForm() {
        alertManager.error("Registration failed. Please correct the errors and try again.");
    }
}