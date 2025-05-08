package com.example.Project2.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import com.example.Project2.services.SessionService;
import com.example.Project2.services.MongoWidgetService;
import com.example.Project2.model.Widget;
import org.apache.tapestry5.alerts.AlertManager;
import java.util.List;

public class AdminDashboard {
    
    private static final Logger logger = LogManager.getLogger(AdminDashboard.class);
    
    @Inject
    private SessionService sessionService;
    
    @Inject
    private MongoWidgetService widgetService;
    
    @Inject
    private AlertManager alertManager;
    
    @Property
    private boolean isAdminUser;
    
    @Property
    private List<Widget> widgets;
    
    @Property
    private Widget currentWidget;
    
    @Property
    private String price;
    
    @Property
    private String stock;
    
    Object onActivate() {
        isAdminUser = sessionService.isAdmin();
        if (!isAdminUser) {
            logger.warn("Non-admin user attempted to access admin dashboard");
            alertManager.error("Access denied. Administrator privileges required.");
            return Index.class;
        }
        return null;
    }
    
    void setupRender() {
        widgets = widgetService.getAllWidgets();
    }

    void onPrepareForSubmit(String widgetId) {
        if (widgetId != null) {
            currentWidget = widgetService.getWidget(widgetId);
        }
    }
    
    Object onSuccessFromUpdateForm(String widgetId) {
        if (widgetId == null || currentWidget == null) {
            alertManager.error("No widget selected for update");
            return this;
        }
        
        try {
            if (price != null && !price.isEmpty()) {
                currentWidget.setPrice(Double.parseDouble(price));
            }
            
            if (stock != null && !stock.isEmpty()) {
                currentWidget.setStockQuantity(Integer.parseInt(stock));
            }
            
            widgetService.updateWidget(currentWidget);
            alertManager.success("Widget updated successfully");
            logger.info("Admin updated widget: {}", currentWidget);
            
        } catch (NumberFormatException e) {
            alertManager.error("Invalid price or stock value. Please enter valid numbers.");
            logger.error("Error updating widget: {}", e.getMessage());
        } catch (Exception e) {
            alertManager.error("Error updating widget: " + e.getMessage());
            logger.error("Error updating widget: {}", e.getMessage());
        }
        
        return this;
    }
    
    public void setDefaults(Widget widget) {
        if (widget != null) {
            price = String.valueOf(widget.getPrice());
            stock = String.valueOf(widget.getStockQuantity());
        }
    }
}