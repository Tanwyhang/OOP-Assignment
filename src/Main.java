import controllers.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AdminController adminController;
    private static RoomController roomController;
    private static PatientController patientController;
    private static DoctorController doctorController;
    private static AppointmentController appointmentController;
    private static MedicalRecordController medicalRecordController;

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

    private static void showLoginScreen() {
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

    private static void showMainMenu() {
        while (true) {
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

    // Sub-menu implementations (partial examples)
    private static void manageRooms() {
        System.out.println("\n=== Room Management ===");
        System.out.println("1. Add Room");
        System.out.println("2. View All Rooms");
        System.out.println("3. Update Room Status");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter room type: ");
                String type = scanner.nextLine();
                roomController.addRoom(type);
                break;
            /*
            case 2:
                roomController.getAllRooms().forEach(room -> 
                    System.out.println("Room ID: " + room.getRoomID() + 
                                    ", Type: " + room.getType() + 
                                    ", Status: " + room.getStatus()));
                break;
            */
            // Add other cases
        }
    }

    private static void searchViewData() {
        System.out.println("\n=== Search/View Data ===");
        System.out.println("1. Search Patients");
        System.out.println("2. Search Doctors");
        System.out.println("3. View Doctors by Department");
        System.out.println("4. View Doctors by Experience");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        /*
        switch (choice) {
            case 1 -> {
                System.out.print("Enter patient name: ");
                String name = scanner.nextLine();
                patientController.searchPatientsByName(name).forEach(patient ->
                        System.out.println("ID: " + patient.getPersonID() + ", Name: " + patient.getName()));
            }
            case 3 -> {
                System.out.print("Enter department: ");
                String department = scanner.nextLine();
                doctorController.getDoctorsByDepartment(department).forEach(doctor ->
                        System.out.println("Dr. " + doctor.getName() + " - " + doctor.getSpecialization()));
                // Add other cases
            }
            // Add other cases
            
        }*/
    }

    private static void changePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        adminController.changePassword(newPassword);
        System.out.println("Password changed successfully!");
    }

    // Add other management methods (managePatients, manageDoctors, etc.)
}