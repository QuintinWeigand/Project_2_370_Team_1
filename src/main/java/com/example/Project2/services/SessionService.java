package com.example.Project2.services;

import com.example.Project2.model.User;
import org.apache.tapestry5.http.services.RequestGlobals;
import org.apache.tapestry5.http.services.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service to manage user sessions in the application.
 */
public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
    private static final String USER_ID_KEY = "userId";
    private static final String USER_ROLE_KEY = "userRole";
    private static final String USERNAME_KEY = "username";
    
    private final RequestGlobals requestGlobals;
    private final UserService userService;

    public SessionService(RequestGlobals requestGlobals, UserService userService) {
        this.requestGlobals = requestGlobals;
        this.userService = userService;
    }
    
    public void login(User user) {
        Session session = getSession(true);
        session.setAttribute(USER_ID_KEY, user.getId());
        session.setAttribute(USER_ROLE_KEY, user.getRole());
        session.setAttribute(USERNAME_KEY, user.getUsername());
        logger.info("User logged in: {}", user.getUsername());
    }
    
    public void logout() {
        Session session = getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute(USERNAME_KEY);
            session.invalidate();
            logger.info("User logged out: {}", username);
        }
    }
    
    public boolean isLoggedIn() {
        Session session = getSession(false);
        return session != null && session.getAttribute(USER_ID_KEY) != null;
    }
    
    public boolean isAdmin() {
        Session session = getSession(false);
        if (session == null) return false;
        
        String role = (String) session.getAttribute(USER_ROLE_KEY);
        return "ADMIN".equals(role);
    }
    
    public User getCurrentUser() {
        Session session = getSession(false);
        if (session == null) return null;
        
        String userId = (String) session.getAttribute(USER_ID_KEY);
        if (userId == null) return null;
        
        return userService.getUserById(userId);
    }
    
    public String getCurrentUsername() {
        Session session = getSession(false);
        if (session == null) return null;
        
        return (String) session.getAttribute(USERNAME_KEY);
    }
    
    private Session getSession(boolean create) {
        return requestGlobals.getRequest().getSession(create);
    }
}