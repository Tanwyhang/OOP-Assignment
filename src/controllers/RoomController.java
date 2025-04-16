package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.Room;
import utils.StringUtils;

public final class RoomController implements ControllerInterface<Room> {
    private final static List<Room> rooms = new ArrayList<>();
    private static final Random random = new Random();

    // load rooms from file when the class is constructed
    static {
        loadRoomsFromFile();
    }

    @Override
    public void saveToFile() {
        saveRoomsToFile();
    }

    @Override
    public void loadFromFile() {
        loadRoomsFromFile();
    }

    @Override
    public List<Room> getAll() {
        return new ArrayList<>(rooms);
    }

    @Override
    public String generateUniqueID() {
        return generateUniqueRoomID();
    }

    // generate unique room id, using while to prevent overlap
    private static String generateUniqueRoomID() {
        while (true) {
            int id = random.nextInt(99) + 1; // Generates a number between 1 and 99
            String candidate = "R" + String.format("%02d", id); // Formats the number to always have two digits (e.g., R01, R02, ..., R99)
            if (!rooms.stream().anyMatch(room -> room.getRoomID().equals(candidate))) {
                return candidate;
            }
        }
    }

    public static boolean addRoom(String type) {
        // Generate a unique RoomID using the existing method
        String newRoomID = generateUniqueRoomID();
        
        // Create a new Room object with the generated ID and specified type
        Room newRoom = new Room(newRoomID, type, "Available");
        
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

    // return boolean whether the room is exist or not
    public static boolean roomExists(String roomID) {
        return rooms.stream().anyMatch(room -> room.getRoomID().equals(roomID));
    }

    private static void saveRoomsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/rooms.csv"))) {
            for (Room room : rooms) {
                writer.write(room.getRoomID() + "," + room.getType() + "," + room.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving rooms to file: " + e.getMessage());
        }
    }

    private static void loadRoomsFromFile() {
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
        System.out.println(StringUtils.beautify("=== All Rooms ==="));
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

    public static void searchRoomsByType(String roomType) {
        // print rooms given type
        System.out.println(StringUtils.beautify("=== Search Rooms By Type ==="));
        // use the same method as display all rooms to display only the rooms of the given type
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            // Filter rooms by type
            List<Room> filteredRooms = rooms.stream()
            .filter(room -> room.getType().equals(roomType))
            .toList();

            if (filteredRooms.isEmpty()) {
            System.out.println("No rooms found of type: " + roomType);
            return;
            }

            int columnCount = 3;
            for (int i = 0; i < filteredRooms.size(); i += columnCount) {
            int end = Math.min(i + columnCount, filteredRooms.size());
            List<Room> rowRooms = filteredRooms.subList(i, end);
        
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
        System.out.println("Total number of rooms of type " + roomType + ": " + rooms.stream().filter(room -> room.getType().equals(roomType)).count());
    }

    public static void searchRoomsByStatus(String status) {
        // print rooms given status
        System.out.println(StringUtils.beautify("=== Search Rooms By Status ==="));
        // use the same method as display all rooms to display only the rooms of the given status
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            // Filter rooms by status
            List<Room> filteredRooms = rooms.stream()
            .filter(room -> room.getStatus().equals(status))
            .toList();

            if (filteredRooms.isEmpty()) {
            System.out.println("No rooms found with status: " + status);
            return;
            }

            int columnCount = 3;
            for (int i = 0; i < filteredRooms.size(); i += columnCount) {
            int end = Math.min(i + columnCount, filteredRooms.size());
            List<Room> rowRooms = filteredRooms.subList(i, end);
        
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
        System.out.println("Total number of rooms with status " + status + ": " + rooms.stream().filter(room -> room.getStatus().equals(status)).count());
    }
}   