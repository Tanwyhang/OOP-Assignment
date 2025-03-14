package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.Room;

public final class RoomController {
    private final static List<Room> rooms = new ArrayList<>();
    private static final Random random = new Random();

    // load rooms from file when the class is constructed
    static {
        loadRoomsFromFile();
    }

    public static boolean addRoom(String type) {
        // Generate a unique RoomID
        Room newRoom = null;

        while (true) { 
            int id = random.nextInt(99) + 1; // Generates a number between 1 and 99
            final String newRoomID = "R" + String.format("%02d", id); // Formats the number to always have two digits (e.g., R01, R02, ..., R99)
            if (!rooms.stream().anyMatch(room -> room.getRoomID().equals(newRoomID))) {
                // Create a new Room object with the generated ID and specified type
                newRoom = new Room(newRoomID, type, "Available");
                break;
            }
        }
        
        // Add the new room to the list
        rooms.add(newRoom);
        saveRoomsToFile();
        return true;
    }

    public static boolean removeRoom(String roomID) {
        boolean removed = rooms.removeIf(room -> room.getRoomID().equals(roomID));
        if (removed) {
            saveRoomsToFile();
        }
        return removed;
    }

    public static void updateRoomStatus(String roomID, String status) {
        rooms.stream()
            .filter(room -> room.getRoomID().equals(roomID))
            .findFirst()
            .ifPresent(room -> {
                room.setStatus(status);
                saveRoomsToFile();
            });
    }

    public Room findAvailableRoom(String type) {
        return rooms.stream()
            .filter(room -> room.getType().equals(type) && room.getStatus().equals("Available"))
            .findFirst()
            .orElse(null);
    }

    public static void saveRoomsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/rooms.csv"))) {
            for (Room room : rooms) {
                writer.write(room.getRoomID() + "," + room.getType() + "," + room.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving rooms to file: " + e.getMessage());
        }
    }

    public static void loadRoomsFromFile() {
        File file = new File("data/rooms.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    rooms.add(new Room(data[0], data[1], data[2]));
                }
            } catch (IOException e) {
                System.err.println("Error loading rooms from file: " + e.getMessage());
            }
        }
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public static String displayAllRooms() {
        System.out.println("\n=== All Rooms ===");
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            int columnCount = 3;
            for (int i = 0; i < rooms.size(); i += columnCount) {
                int end = Math.min(i + columnCount, rooms.size());
                List<Room> rowRooms = rooms.subList(i, end);
    
                // Prepare each line part for all rooms in the current row
                List<String> topParts = new ArrayList<>();
                List<String> line1Parts = new ArrayList<>();
                List<String> line2Parts = new ArrayList<>();
                List<String> bottomParts = new ArrayList<>();
    
                for (Room room : rowRooms) {
                    // Room availability and color
                    String availability = room.getStatus().equalsIgnoreCase("available") ? "Available" : "Occupied";
                    String color = availability.equalsIgnoreCase("Available") ? "\u001B[32m" : "\u001B[31m";
                    String reset = "\u001B[0m";
    
                    // Top and bottom border parts
                    topParts.add("     +-----------------+");
                    bottomParts.add("     +-----------------+");
    
                    // Line 1: Room ID and Availability
                    String roomID = color + room.getRoomID() + reset;
                    String availText = color + availability + reset;
                    String line1 = String.format("     | %-12s[%18s]  |", roomID, availText);
                    line1Parts.add(line1);
    
                    // Line 2: Room Type
                    String typeText = "Type: " + room.getType();
                    String line2 = String.format("     | %-16s|", typeText);
                    line2Parts.add(line2);
                }
    
                // Print combined lines for the current row
                System.out.println(String.join("   ", topParts));
                System.out.println(String.join("   ", line1Parts));
                System.out.println(String.join("   ", line2Parts));
                System.out.println(String.join("   ", bottomParts));
                System.out.println(); // Empty line after each row
            }
        }
        return null;
    }
}   