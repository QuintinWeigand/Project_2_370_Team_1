package com.example.Project2.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoStatusService {
    private String status;

    public MongoStatusService() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("admin");
            db.runCommand(new Document("ping", 1));
            status = "Connected";
        } catch (Exception e) {
            status = "Not Connected: " + e.getMessage();
        }
    }

    public String getStatus() {
        return status;
    }
}
