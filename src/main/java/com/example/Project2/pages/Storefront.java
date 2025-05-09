package com.example.Project2.pages;

import com.example.Project2.model.Widget;
import com.example.Project2.services.MongoWidgetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * Storefront page to display the available purple widgets.
 */
public class Storefront {
    
    private static final Logger logger = LogManager.getLogger(Storefront.class);
    
    @Inject
    private MongoWidgetService widgetService;
    
    @Property
    private List<Widget> widgets;
    
    @Property
    private Widget widget;
    
    // Setup render is called by Tapestry before the page is rendered
    void setupRender() {
        widgets = widgetService.getAllWidgets();
        logger.info("Loading storefront with {} widgets from MongoDB", widgets.size());
    }
}