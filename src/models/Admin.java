package hospital.management.system.models;

public class Admin {
    private String userID;
    private String password;

    public Admin() {}

    public Admin(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public boolean login(String userID, String password) {
        return this.userID.equals(userID) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("Admin logged out.");
    }

    public boolean changePassword(String newPassword) {
        this.password = newPassword;
        return true;
    }
}