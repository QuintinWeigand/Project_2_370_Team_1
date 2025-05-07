package com.example.Project2.pages;

import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.alerts.AlertManager;
import com.example.Project2.services.MongoStatusService;

public class About {

    @PageActivationContext
    private String learn;

    @Inject
    private MongoStatusService mongoStatusService;

    @Inject
    private AlertManager alertManager;

    public String getLearn() {
        return learn;
    }

    public void setLearn(String learn) {
        this.learn = learn;
    }

    void setupRender() {
        String status = mongoStatusService.getStatus();
        if (status.startsWith("Connected")) {
            alertManager.success("MongoDB Connection Status: " + status);
        } else {
            alertManager.error("MongoDB Connection Status: " + status);
        }
    }
}
