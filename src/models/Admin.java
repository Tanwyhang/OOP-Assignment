package models;

public class Admin {
    private String userID;
    private String password;

    public Admin() {}

    public Admin(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    // Getters and Setters
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}