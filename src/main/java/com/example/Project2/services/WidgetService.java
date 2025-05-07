package com.example.Project2.services;

import com.example.Project2.model.Widget;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for managing widgets in the storefront.
 */
public class WidgetService {
    private static final Logger logger = LoggerFactory.getLogger(WidgetService.class);
    private final Map<String, Widget> widgets = new HashMap<>();
    private long nextId = 1;

    @Startup
    public void initialize() {
        // Add some initial widgets to the store
        addWidget(new Widget(null, "Purple Wonder", 
            "This amazing purple widget will bring joy to your life!", 19.99, 
            "https://via.placeholder.com/200x200/800080/FFFFFF?text=Purple+Wonder", 10));
        
        addWidget(new Widget(null, "Purple Elegance", 
            "An elegant purple widget for the sophisticated customer.", 29.99, 
            "https://via.placeholder.com/200x200/800080/FFFFFF?text=Purple+Elegance", 5));
        
        addWidget(new Widget(null, "Purple Power", 
            "The most powerful purple widget on the market!", 39.99, 
            "https://via.placeholder.com/200x200/800080/FFFFFF?text=Purple+Power", 3));
        
        addWidget(new Widget(null, "Purple Mini", 
            "A small but mighty purple widget for those on the go.", 14.99, 
            "https://via.placeholder.com/200x200/800080/FFFFFF?text=Purple+Mini", 15));
        
        addWidget(new Widget(null, "Purple Pro", 
            "The professional-grade purple widget for serious widget enthusiasts.", 49.99, 
            "https://via.placeholder.com/200x200/800080/FFFFFF?text=Purple+Pro", 2));
            
        logger.info("Initialized widget service with {} widgets", widgets.size());
    }

    public Widget getWidget(String id) {
        return widgets.get(id);
    }

    public List<Widget> getAllWidgets() {
        return new ArrayList<>(widgets.values());
    }

    public Widget addWidget(Widget widget) {
        if (widget.getId() == null) {
            widget.setId(String.valueOf(nextId++));
        }
        widgets.put(widget.getId(), widget);
        return widget;
    }

    public void updateWidget(Widget widget) {
        if (widget.getId() != null && widgets.containsKey(widget.getId())) {
            widgets.put(widget.getId(), widget);
        } else {
            throw new IllegalArgumentException("Cannot update widget: widget not found or has no ID");
        }
    }

    public void deleteWidget(String id) {
        widgets.remove(id);
    }
}