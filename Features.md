# Features

## User Authentication

- User registration with email and password
- Secure password storage using BCrypt hashing
- Session management for logged-in users
- Role-based access control (Admin and User roles)

## Storefront

- Browse available purple widgets with images and descriptions
- View widget details including:
  - Name and description
  - Price
  - Stock quantity
  - Product images
- Responsive design for desktop and mobile devices

## Admin Dashboard

- Secure admin interface accessible only to administrators
- Widget management capabilities:
  - View all widgets in inventory
  - Update widget prices
  - Modify stock quantities
- Default admin account for initial setup

## Technical Features

- MongoDB integration for data persistence
- Docker and Docker Compose support for easy deployment
- Apache Tapestry 5.8.0 framework
- Environment variable configuration for MongoDB connection
- Comprehensive logging system using Log4j2
- Integration tests using Selenium WebDriver