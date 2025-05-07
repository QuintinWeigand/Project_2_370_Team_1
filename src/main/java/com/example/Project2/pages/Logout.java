package com.example.Project2.pages;

import com.example.Project2.services.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Page for logging out users.
 */
public class Logout {
    
    private static final Logger logger = LogManager.getLogger(Logout.class);
    
    @Inject
    private SessionService sessionService;
    
    @Inject
    private AlertManager alertManager;
    
    @Log
    Object onActivate() {
        if (sessionService.isLoggedIn()) {
            String username = sessionService.getCurrentUsername();
            sessionService.logout();
            alertManager.info("You have been logged out successfully.");
            logger.info("User logged out: {}", username);
        }
        
        return Index.class;
    }
}