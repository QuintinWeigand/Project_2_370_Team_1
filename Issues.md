# Known Issues and Solutions

## Selenium Test Issues

### GeckoDriver Requirement
**Issue:** Selenium tests fail with "geckodriver not found" error.  
**Solution:** Install GeckoDriver using your system's package manager:
```bash
# Ubuntu/Debian
sudo apt install firefox-geckodriver

# Fedora
sudo dnf install firefox-geckodriver

# Arch Linux
sudo pacman -S geckodriver
```

### Firefox Dependencies
**Issue:** Tests fail due to missing Firefox browser.  
**Solution:** Ensure Firefox is installed and up-to-date. The application's Dockerfile includes Firefox installation for containerized testing.

## MongoDB Connection Issues

### Connection Refused
**Issue:** Application fails to connect to MongoDB with "Connection refused" error.  
**Solution:** 
1. Verify MongoDB is running: `systemctl status mongodb`
2. Check MongoDB logs: `journalctl -u mongodb`
3. Ensure correct environment variables are set for `MONGODB_HOST` and `MONGODB_PORT`

### Docker Networking
**Issue:** Application container cannot connect to MongoDB container.  
**Solution:** Docker Compose includes proper container networking and health checks. Wait for MongoDB container to be healthy before accessing the application.

## Build and Deployment Issues

### JDK Version
**Issue:** Build fails due to incorrect Java version.  
**Solution:** Ensure JDK 17 is installed and set as the default Java version:
```bash
sudo update-alternatives --config java
```

### Maven Dependencies
**Issue:** Missing dependencies during build.  
**Solution:** The project includes all necessary dependencies in pom.xml. Run:
```bash
mvn clean install -U
```
The `-U` flag forces update of dependencies.

## Workarounds and Tips

1. If running tests locally, ensure Firefox is not running before executing tests
2. For development, use the `mvn jetty:run` command which provides faster startup
3. Monitor application logs in `build/exceptions/` for detailed error tracking
4. Use Docker Compose for the most reliable deployment experience