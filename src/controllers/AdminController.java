package controllers;


import java.io.*;
import models.Admin;

public class AdminController {
    private static final String ADMIN_FILE = "data/admin.csv";
    private Admin admin;

    public AdminController() {
        loadAdminFromFile();
    }

    public boolean login(String userID, String password) {
        return admin != null && admin.getUserID().equals(userID) && admin.getPassword().equals(password);
    }

    public void changePassword(String newPassword) {
        admin.setPassword(newPassword);
        saveAdminToFile();
    }

    private void loadAdminFromFile() {
        File file = new File(ADMIN_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String[] data = reader.readLine().split(",");
                admin = new Admin(data[0], data[1]);
            } catch (IOException e) {
                System.err.println("Error loading admin: " + e.getMessage());
            }
        } else {
            // Default admin credentials
            admin = new Admin("admin", "admin123");
            saveAdminToFile();
        }
    }

    private void saveAdminToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            writer.write(admin.getUserID() + "," + admin.getPassword());
        } catch (IOException e) {
            System.err.println("Error saving admin: " + e.getMessage());
        }
    }
}