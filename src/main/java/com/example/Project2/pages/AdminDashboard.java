package com.example.Project2.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import com.example.Project2.services.SessionService;
import org.apache.tapestry5.alerts.AlertManager;

public class AdminDashboard {
    
    private static final Logger logger = LogManager.getLogger(AdminDashboard.class);
    
    @Inject
    private SessionService sessionService;
    
    @Inject
    private AlertManager alertManager;
    
    @Property
    private boolean isAdminUser;
    
    Object onActivate() {
        isAdminUser = sessionService.isAdmin();
        if (!isAdminUser) {
            logger.warn("Non-admin user attempted to access admin dashboard");
            alertManager.error("Access denied. Administrator privileges required.");
            return Index.class;
        }
        return null;
    }
}