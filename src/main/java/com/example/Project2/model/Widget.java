package com.example.Project2.model;

/**
 * Represents a Purple Widget product in the system.
 */
public class Widget {
    private String id; // Changed from Long to String for MongoDB ObjectId
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String color = "purple"; // Default color is purple
    private int stockQuantity;

    public Widget() {
        // Default constructor
    }

    public Widget(String id, String name, String description, double price, String imageUrl, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stockQuantity = stockQuantity;
    }

    // Getters and setters
    public String getId() {  // Changed from Long to String
        return id;
    }

    public void setId(String id) {  // Changed from Long to String
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Widget{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}