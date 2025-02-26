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
        //showLoginScreen();
        showMainMenu();
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
            System.out.println(StringConstants.MAIN_MENU);
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // MAIN MENU CHOICE HANDLING 
            switch (choice) {
                case 1: manageRooms(); break;
                case 2: managePatients(); break;
                //case 3: manageDoctors(); break;
                //case 4: manageAppointments(); break;
                //case 5: manageMedicalRecords(); break;
                case 6: searchViewData(); break;
                case 7: changePassword(); break;
                case 0: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // 1. MANAGE ROOMS
    private static void manageRooms() {
        while (true) {
            clearScreen();
            roomController.displayAllRooms();
            System.out.println(StringConstants.ROOM_MENU);
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> {break;} // Exit to main menu
                // Add Room
                case 1 -> {
                    while (true) { // Loop until valid room type is chosen
                        clearScreen();
                        roomController.displayAllRooms();
                        System.out.println(StringConstants.ROOM_TYPE_MENU);
                        System.out.print("Choose an option: ");
                        int roomTypeChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                    
                        String type;
                        switch (roomTypeChoice) {
                            case 0 -> {
                                type = null;
                                break;
                            } // Exit to "Manage Rooms"
                            case 1 -> type = "ICU";
                            case 2 -> type = "Ward";
                            case 3 -> type = "Emergency";
                            case 4 -> type = "Operation";
                            case 5 -> type = "Isolation";
                            default -> {
                                System.out.println("Invalid room type choice!");
                                pause();
                                continue; // Restart loop
                            }
                        }
                        // if not invalid (valid), add room
                        if (type != null) {
                            roomController.addRoom(type);
                            System.out.println("Room added successfully!");
                            pause();
                        }
                        break;
                    }
                }

                // Remove Room
                case 2 -> {
                    clearScreen();
                    roomController.displayAllRooms();
                    System.out.print("Enter room ID to remove: ");
                    String roomID = scanner.nextLine();
                    if (roomController.removeRoom(roomID)) {
                        System.out.println("Room removed successfully!");
                    } else {
                        System.out.println("Room not found!");
                    }
                    pause();
                }

                // Update Room Status
                case 3 -> {
                    clearScreen();
                    roomController.displayAllRooms();
                    System.out.print("Enter room ID: ");
                    String roomID = scanner.nextLine();
                    System.out.print("Assign new status:");
                    System.out.print("\n[1] Available\n[2] Occupied\nChoose an option: ");
                    int status = scanner.nextInt();
                    switch (status) {
                        case 1 -> {roomController.updateRoomStatus(roomID, "available");}
                        case 2 -> {roomController.updateRoomStatus(roomID, "unavailable");}
                        default -> throw new AssertionError();
                    }
                    
                    System.out.println("Room status updated successfully!");
                }
                default -> System.out.println("Invalid choice!");
            }
            break;
        }
    }

    // 2. MANAGE PATIENTS
    private static void managePatients() {
        while (true) {
            clearScreen();
            System.out.println(StringConstants.PATIENT_MENU);
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> {break;} // Exit to main menu
                // Add Patient
                case 1 -> {
                    clearScreen();
                    System.out.print("Enter patient name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter patient age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter patient gender (M/F): ");
                    char gender = scanner.nextLine().charAt(0);
                    
                    System.out.print("Enter patient address: ");
                    String address = scanner.nextLine();

                    System.out.print("Enter patient phone number: ");
                    String phoneNumber = scanner.nextLine();

                    patientController.registerPatient(name, address, phoneNumber, gender, age);
                    System.out.println("Patient added successfully!");
                    scanner.nextLine();
                }
            }
        }
    }

    // 6. SEARCH/VIEW DATA
    private static void searchViewData() {
        clearScreen();
        System.out.println(StringConstants.SEARCH_VIEW_MENU);
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
    // 7. CHANGE PASSWORD
    private static void changePassword() {
        System.out.print("Enter current password: ");
        String oldPassword = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        if (adminController.changePassword(oldPassword, newPassword)) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Invalid current password! Try again.");
        }
        
        pause();
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

    private static void pause() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}