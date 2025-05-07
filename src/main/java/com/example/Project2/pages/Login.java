package com.example.Project2.pages;

import com.example.Project2.model.User;
import com.example.Project2.services.SessionService;
import com.example.Project2.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Login {

    private static final Logger logger = LogManager.getLogger(Login.class);

    @Inject
    private AlertManager alertManager;

    @Inject
    private UserService userService;
    
    @Inject
    private SessionService sessionService;

    @InjectComponent
    private Form login;

    @InjectComponent("username")
    private TextField usernameField;

    @InjectComponent("password")
    private PasswordField passwordField;

    @Property
    private String username;

    @Property
    private String password;
    
    // Called when first visiting the login page
    void onActivate() {
        // If already logged in, redirect to index
        if (sessionService.isLoggedIn()) {
            alertManager.info("You are already logged in as " + sessionService.getCurrentUsername());
        }
    }

    void onValidateFromLogin() {
        if (username == null || username.isEmpty()) {
            login.recordError(usernameField, "Username is required");
            return;
        }
        
        if (password == null || password.isEmpty()) {
            login.recordError(passwordField, "Password is required");
            return;
        }
        
        User user = userService.authenticateUser(username, password);
        if (user == null) {
            login.recordError("Invalid username or password");
        }
    }

    Object onSuccessFromLogin() {
        User user = userService.getUserByUsername(username);
        sessionService.login(user);
        
        logger.info("Login successful for user: {}", username);
        alertManager.success("Welcome, " + username + "!");
        
        return Index.class;
    }

    void onFailureFromLogin() {
        logger.warn("Login failed for username: {}", username);
        alertManager.error("Login failed. Please check your credentials.");
    }
}
