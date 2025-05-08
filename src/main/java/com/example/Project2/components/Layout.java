package com.example.Project2.components;

import com.example.Project2.services.SessionService;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import java.time.LocalDate;

/**
 * Layout component for pages of application test-project.
 */
@Import(stylesheet = "context:css/app.css")
public class Layout {

    @Inject
    private ComponentResources resources;
    
    @Inject
    private SessionService sessionService;

    /**
    * The page title, for the <title> element and the <h1> element.
    */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;

    public String getClassForPageName() {
        return resources.getPageName().equalsIgnoreCase(pageName) ? "nav-link active" : "nav-link";
    }

    public String[] getPageNames() {
        return new String[]{ "Index", "Storefront", "About", "AdminDashboard" };
    }
    
    public boolean isLoggedIn() {
        return sessionService.isLoggedIn();
    }
    
    public boolean isAdmin() {
        return sessionService.isAdmin();
    }
    
    public String getCurrentUsername() {
        return sessionService.getCurrentUsername();
    }

    public int getYear() {
        return LocalDate.now().getYear();
    }
}
