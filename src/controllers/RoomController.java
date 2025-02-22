package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import models.Room;

public class RoomController {
    private List<Room> rooms;

    public RoomController() {
        this.rooms = new ArrayList<>();
        loadRoomsFromFile();
    }

    public boolean addRoom(String type) {
        Room newRoom = new Room("R" + (rooms.size() + 1), type, "Available");
        rooms.add(newRoom);
        saveRoomsToFile();
        return true;
    }

    public boolean removeRoom(String roomID) {
        boolean removed = rooms.removeIf(room -> room.getRoomID().equals(roomID));
        if (removed) {
            saveRoomsToFile();
        }
        return removed;
    }

    public void updateRoomStatus(String roomID, String status) {
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

    public void saveRoomsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/rooms.csv"))) {
            for (Room room : rooms) {
                writer.write(room.getRoomID() + "," + room.getType() + "," + room.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving rooms to file: " + e.getMessage());
        }
    }

    public void loadRoomsFromFile() {
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
}   