package com.example.Project2.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoStatusService {
    private String status;

    public MongoStatusService() {
        String mongoHost = System.getenv("MONGODB_HOST");
        String mongoPort = System.getenv("MONGODB_PORT");
        String mongoUrl = String.format("mongodb://%s:%s", 
            mongoHost != null ? mongoHost : "localhost",
            mongoPort != null ? mongoPort : "27017");
            
        try (MongoClient mongoClient = MongoClients.create(mongoUrl)) {
            MongoDatabase db = mongoClient.getDatabase("admin");
            db.runCommand(new Document("ping", 1));
            status = "Connected to " + mongoUrl;
        } catch (Exception e) {
            status = "Not Connected to " + mongoUrl + ": " + e.getMessage();
        }
    }

    public String getStatus() {
        return status;
    }
}
