import controllers.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static AdminController adminController;
    private static RoomController roomController;
    private static PatientController patientController;
    private static DoctorController doctorController;
    private static AppointmentController appointmentController;
    private static MedicalRecordController medicalRecordController;

    // MAIN ENTRY POINT
    public static void main(String[] args) {
        initializeControllers();
        showLoginScreen();
    }

    private static void initializeControllers() {
        adminController = new AdminController();
        roomController = new RoomController();
        patientController = new PatientController();
        doctorController = new DoctorController();
        appointmentController = new AppointmentController();
        medicalRecordController = new MedicalRecordController();
    }

    // LOGIN SCREEN
    private static void showLoginScreen() {
        clearScreen();
        System.out.println("\n=== Hospital Management System Login ===");
        System.out.print("Enter Admin ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (adminController.login(userID, password)) {
            showMainMenu();
        } else {
            System.out.println("Invalid credentials! Try again.");
            showLoginScreen();
        }
    }

    // -> MAIN MENU
    private static void showMainMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Manage Rooms");
            System.out.println("2. Manage Patients");
            System.out.println("3. Manage Doctors");
            System.out.println("4. Manage Appointments");
            System.out.println("5. Medical Records");
            System.out.println("6. Search/View Data");
            System.out.println("7. Change Password");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // MAIN MENU CHOICE HANDLING 
            switch (choice) {
                case 1: manageRooms(); break;
                //case 2: managePatients(); break;
                //case 3: manageDoctors(); break;
                //case 4: manageAppointments(); break;
                //case 5: manageMedicalRecords(); break;
                case 6: searchViewData(); break;
                case 7: changePassword(); break;
                case 8: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void manageRooms() {
        clearScreen();
        System.out.println("\n=== Room Management ===");
        System.out.println("1. Add Room");
        System.out.println("2. View All Rooms");
        System.out.println("3. Update Room Status");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.print("Enter room type: ");
                String type = scanner.nextLine();
                roomController.addRoom(type);
                System.out.println("Room added successfully!");
            }
            case 2 -> {
                clearScreen();
                System.out.println("\n=== All Rooms ===");
                roomController.getAllRooms().forEach(room ->
                    System.out.println("Room ID: " + room.getRoomID() +
                                      ", Type: " + room.getType() +
                                      ", Status: " + room.getStatus()));
            }
            case 3 -> {
                System.out.print("Enter room ID: ");
                String roomID = scanner.nextLine();
                System.out.print("Enter new status (available/unavailable): ");
                String status = scanner.nextLine();
                roomController.updateRoomStatus(roomID, status);
                System.out.println("Room status updated successfully!");
            }
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void searchViewData() {
        clearScreen();
        System.out.println("\n=== Search/View Data ===");
        System.out.println("1. Search Patients");
        System.out.println("2. Search Doctors");
        System.out.println("3. View Doctors by Department");
        System.out.println("4. View Doctors by Experience");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.print("Enter patient name: ");
                String name = scanner.nextLine();
                clearScreen();
                System.out.println("\n=== Search Results ===");
                patientController.searchPatientsByName(name).forEach(patient ->
                    System.out.println("ID: " + patient.getPersonID() + ", Name: " + patient.getName()));
            }
            case 2 -> {
                System.out.print("Enter doctor name: ");
                String doctorName = scanner.nextLine();
                clearScreen();
                System.out.println("\n=== Search Results ===");
                doctorController.searchDoctorsByName(doctorName).forEach(doctor ->
                    System.out.println("Dr. " + doctor.getName() + " - " + doctor.getSpecialization()));
            }
            case 3 -> {
                System.out.print("Enter department: ");
                String department = scanner.nextLine();
                clearScreen();
                System.out.println("\n=== Doctors by Department ===");
                doctorController.getDoctorsByDepartment(department).forEach(doctor ->
                    System.out.println("Dr. " + doctor.getName() + " - " + doctor.getSpecialization()));
            }
            case 4 -> {
                System.out.print("Enter minimum years of experience: ");
                int yearsOfExperience = scanner.nextInt();
                scanner.nextLine();
                clearScreen();
                System.out.println("\n=== Doctors by Experience ===");
                doctorController.getDoctorsByExperience(yearsOfExperience).forEach(doctor ->
                    System.out.println("Dr. " + doctor.getName() + " - " + doctor.getYearsOfExperience() + " years"));
            }
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void changePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        adminController.changePassword(newPassword);
        System.out.println("Password changed successfully!");
        scanner.nextLine();
    }

    // Utility Methods
    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) { // For Windows systems
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else { // For Unix/Linux/Mac systems
                System.out.print("\033[H\033[2J"); System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error clearing screen: " + e.getMessage());
        }
    }
}