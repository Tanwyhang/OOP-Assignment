package models;

public class Room {
    private String roomID;
    private String type;
    private String status;

    public Room() {}

    public Room(String roomID, String type, String status) {
        this.roomID = roomID;
        this.type = type;
        this.status = status;
    }

    // Getters and Setters
    public String getRoomID() { return roomID; }
    public String getType() { return type; }
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
}