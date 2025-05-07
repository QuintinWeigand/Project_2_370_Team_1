package com.example.Project2.services;

import com.example.Project2.model.Widget;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.apache.tapestry5.ioc.annotations.PostInjection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MongoWidgetService {
    private static final Logger logger = LoggerFactory.getLogger(MongoWidgetService.class);
    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;

    public MongoWidgetService() {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("Widgets");
        this.collection = database.getCollection("stock");
        logger.info("Connected to MongoDB Widgets database");
    }

    @PostInjection
    public void initialize() {
        // Only add initial data if collection is empty
        if (collection.countDocuments() == 0) {
            addInitialWidgets();
        }
    }

    private void addInitialWidgets() {
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
    }

    public Widget getWidget(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        return doc != null ? documentToWidget(doc) : null;
    }

    public List<Widget> getAllWidgets() {
        List<Widget> widgets = new ArrayList<>();
        collection.find().forEach(doc -> widgets.add(documentToWidget(doc)));
        return widgets;
    }

    public Widget addWidget(Widget widget) {
        Document doc = widgetToDocument(widget);
        collection.insertOne(doc);
        widget.setId(doc.getObjectId("_id").toString());
        return widget;
    }

    public void updateWidget(Widget widget) {
        Document doc = widgetToDocument(widget);
        collection.replaceOne(
            Filters.eq("_id", new ObjectId(widget.getId())),
            doc,
            new ReplaceOptions().upsert(true)
        );
    }

    public void deleteWidget(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    private Document widgetToDocument(Widget widget) {
        Document doc = new Document()
            .append("name", widget.getName())
            .append("description", widget.getDescription())
            .append("price", widget.getPrice())
            .append("imageUrl", widget.getImageUrl())
            .append("color", widget.getColor())
            .append("stockQuantity", widget.getStockQuantity());
        
        if (widget.getId() != null) {
            doc.append("_id", new ObjectId(widget.getId()));
        }
        return doc;
    }

    private Widget documentToWidget(Document doc) {
        Widget widget = new Widget();
        widget.setId(doc.getObjectId("_id").toString());
        widget.setName(doc.getString("name"));
        widget.setDescription(doc.getString("description"));
        widget.setPrice(doc.getDouble("price"));
        widget.setImageUrl(doc.getString("imageUrl"));
        widget.setColor(doc.getString("color"));
        widget.setStockQuantity(doc.getInteger("stockQuantity"));
        return widget;
    }
}