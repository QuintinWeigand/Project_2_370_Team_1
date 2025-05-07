package com.example.Project2.services;

import com.example.Project2.model.User;
import com.example.Project2.util.PasswordUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.apache.tapestry5.ioc.annotations.PostInjection;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing user accounts in the MongoDB database.
 */
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;

    public UserService() {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("Widgets");
        this.collection = database.getCollection("Users");
        logger.info("Connected to MongoDB Users collection");
    }

    @PostInjection
    @Startup
    public void initialize() {
        // Check if admin user exists, if not create it
        if (getUserByUsername("admin") == null) {
            createDefaultAdminUser();
        }
    }

    private void createDefaultAdminUser() {
        User adminUser = new User(
            null,
            "admin",
            "admin@purplewidgets.com",
            PasswordUtil.hashPassword("admin"),
            "ADMIN"
        );
        addUser(adminUser);
        logger.info("Created default admin user");
    }

    public User getUserById(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        return doc != null ? documentToUser(doc) : null;
    }

    public User getUserByUsername(String username) {
        Document doc = collection.find(Filters.eq("username", username)).first();
        return doc != null ? documentToUser(doc) : null;
    }

    public User getUserByEmail(String email) {
        Document doc = collection.find(Filters.eq("email", email)).first();
        return doc != null ? documentToUser(doc) : null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        collection.find().forEach(doc -> users.add(documentToUser(doc)));
        return users;
    }

    public User addUser(User user) {
        // Check if username or email already exists
        if (getUserByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (getUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        Document doc = userToDocument(user);
        collection.insertOne(doc);
        user.setId(doc.getObjectId("_id").toString());
        return user;
    }

    public void updateUser(User user) {
        // Check if user exists
        if (getUserById(user.getId()) == null) {
            throw new IllegalArgumentException("User not found");
        }

        Document doc = userToDocument(user);
        collection.replaceOne(
            Filters.eq("_id", new ObjectId(user.getId())),
            doc,
            new ReplaceOptions().upsert(true)
        );
    }

    public void deleteUser(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    public User authenticateUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    private Document userToDocument(User user) {
        Document doc = new Document()
            .append("username", user.getUsername())
            .append("email", user.getEmail())
            .append("passwordHash", user.getPasswordHash())
            .append("role", user.getRole());
        
        if (user.getId() != null) {
            doc.append("_id", new ObjectId(user.getId()));
        }
        return doc;
    }

    private User documentToUser(Document doc) {
        User user = new User();
        user.setId(doc.getObjectId("_id").toString());
        user.setUsername(doc.getString("username"));
        user.setEmail(doc.getString("email"));
        user.setPasswordHash(doc.getString("passwordHash"));
        user.setRole(doc.getString("role"));
        return user;
    }
}