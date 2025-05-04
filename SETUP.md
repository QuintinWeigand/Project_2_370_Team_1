# Contributor Setup Guide

To ensure the project runs smoothly, please make sure the following dependencies are installed on your Linux system:

1. **Geckodriver**  
   Install Geckodriver using your package manager. For example, on Ubuntu/Debian-based systems, run:  
   ```bash
   sudo apt install firefox-geckodriver
   ```

2. **JDK 17**  
   Install JDK 17 using your package manager. For example, on Ubuntu/Debian-based systems, run:  
   ```bash
   sudo apt install openjdk-17-jdk
   ```
   After installation, set JDK 17 as the default Java version:
   ```bash
   sudo update-alternatives --config java
   ```
   Select the number corresponding to JDK 17 from the list.

3. **Firefox Browser**  
   Ensure that the Firefox browser is installed and up-to-date. You can install it using your package manager:  
   ```bash
   sudo apt install firefox
   ```

## If These Commands Do Not Apply to Your Distro  
If the above commands do not work for your Linux distribution, please refer to your distro's documentation or package manager to install the required dependencies.

---

*Authored by Quinn*