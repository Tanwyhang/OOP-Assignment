import controllers.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static AdminController adminController;

    // MAIN ENTRY POINT
    public static void main(String[] args) {
        initializeControllers();
        showLoginScreen();
        showMainMenu();
    }

    private static void initializeControllers() {
        adminController = new AdminController();
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
                case 1:
                    manageRooms();
                    break;
                case 2:
                    managePatients();
                    break;
                case 3:
                    manageDoctors();
                    break;
                case 4:
                    manageAppointments();
                    break;
                case 5: 
                    manageMedicalRecords(); 
                    break;
                case 6:
                    searchViewData();
                    break;
                case 7:
                    changePassword();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 1. MANAGE ROOMS
    private static void manageRooms() {
        boolean managingRooms = true;
        while (managingRooms) {
            clearScreen();
            RoomController.displayAllRooms();
            System.out.println(StringConstants.ROOM_MENU);
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> {
                    managingRooms = false;
                } // Exit to main menu
                  // Add Room
                case 1 -> {
                    while (true) { // Loop until valid room type is chosen
                        clearScreen();
                        RoomController.displayAllRooms();
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
                            RoomController.addRoom(type);
                            System.out.println("Room added successfully!");
                            pause();
                        }
                        break;
                    }
                }

                // Remove Room
                case 2 -> {
                    clearScreen();
                    RoomController.displayAllRooms();
                    System.out.print("Enter room ID to remove: ");
                    String roomID = scanner.nextLine();
                    if (RoomController.removeRoom(roomID)) {
                        System.out.println("Room removed successfully!");
                    } else {
                        System.out.println("Room not found!");
                    }
                    pause();
                }

                // Update Room Status
                case 3 -> {
                    clearScreen();
                    RoomController.displayAllRooms();
                    System.out.print("Enter room ID: ");
                    String roomID = scanner.nextLine();
                    System.out.print("Assign new status:");
                    System.out.print("\n[1] Available\n[2] Occupied\nChoose an option: ");
                    int status = scanner.nextInt();
                    switch (status) {
                        case 1 -> {
                            RoomController.updateRoomStatus(roomID, "available");
                        }
                        case 2 -> {
                            RoomController.updateRoomStatus(roomID, "unavailable");
                        }
                        default -> throw new AssertionError();
                    }

                    System.out.println("Room status updated successfully!");
                }
                default -> System.out.println("Invalid choice!");
            }
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
                case 0 -> {
                    showMainMenu();
                } // Exit to main menu
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

                    PatientController.registerPatient(name, address, phoneNumber, gender, age);
                    System.out.println("Patient added successfully!");
                    scanner.nextLine();
                }
                // Remove Patient
                case 2 -> {
                    clearScreen();
                    PatientController.displayAllPatients();
                    System.out.print("\nEnter patient ID to remove: ");
                    String patientID = scanner.nextLine();
                    if (PatientController.dischargePatient(patientID)) {
                        System.out.println("Patient removed successfully!");
                    } else {
                        System.out.println("Patient not found!");
                    }
                    pause();
                }
                // Update Patient Details
                case 3 -> {
                    clearScreen();
                    PatientController.displayAllPatients();
                    System.out.print("Enter patient ID to update: ");
                    String patientID = scanner.nextLine();

                    System.out.print("\nEnter new name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter new age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new gender (M/F): ");
                    char gender = scanner.nextLine().charAt(0);
                    System.out.print("Enter new address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String phoneNumber = scanner.nextLine();

                    PatientController.updatePatientDetails(patientID, name, address, phoneNumber, gender, age);
                    System.out.println("\nPatient details updated successfully!");
                    pause();
                }

                // View Patient Details
                case 4 -> {
                    clearScreen();
                    PatientController.displayAllPatients();
                    System.out.print("Enter patient ID to view details: ");
                    String patientID = scanner.nextLine();
                    PatientController.viewPatientDetails(patientID);
                    pause();
                }
            }
        }
    }

    // 3. MANAGE DOCTORS
    public static void manageDoctors() {
        while (true) {
            clearScreen();
            System.out.println(StringConstants.DOCTOR_MENU);
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> {
                    showMainMenu();
                } // Exit to main menu
                  // Add Doctor
                case 1 -> {
                    clearScreen();
                    System.out.print("Enter doctor name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter doctor age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter doctor gender (M/F): ");
                    char gender = scanner.nextLine().charAt(0);
                    System.out.print("Enter doctor address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter doctor phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter doctor department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter doctor shift: ");
                    String shift = scanner.nextLine();
                    System.out.print("Enter doctor years of experience: ");
                    int yearsOfExperience = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter doctor specialization: ");
                    String specialization = scanner.nextLine();
                    DoctorController.hireDoctor(name, address, phoneNumber, gender, age, department, shift,
                            yearsOfExperience, specialization);
                    System.out.println("Doctor added successfully!");
                    pause();
                }
                // Remove Doctor
                case 2 -> {
                    clearScreen();
                    DoctorController.displayAllDoctors();
                    System.out.print("Enter doctor ID to remove: ");
                    String doctorID = scanner.nextLine();
                    if (DoctorController.removeDoctor(doctorID)) {
                        System.out.println("Doctor removed successfully!");
                    } else {
                        System.out.println("Doctor not found!");
                    }
                    pause();
                }
                // Update Doctor Details
                case 3 -> {
                    clearScreen();
                    DoctorController.displayAllDoctors();
                    System.out.print("Enter doctor ID to update: ");
                    String doctorID = scanner.nextLine();
                    System.out.print("Enter new specialization: ");
                    String specialization = scanner.nextLine();
                    DoctorController.updateSpecialization(doctorID, specialization);
                    System.out.println("Doctor specialization updated successfully!");
                    pause();
                }
                // View Doctors
                case 4 -> {
                    clearScreen();
                    DoctorController.displayAllDoctors();
                    // Enter PersonID to view details
                    System.out.print("Enter doctor ID to view details: ");
                    String personID = scanner.nextLine();
                    DoctorController.viewDoctorDetails(personID);
                    pause();
                }
            }
        }
    }

    // 4. MANAGE APPOINTMENT
    private static void manageAppointments(){
        while (true){
            clearScreen();
            System.out.println(StringConstants.APPOINTMENT_MENU);
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 0 -> {showMainMenu();}

                case 1 -> {
                    clearScreen();

                    // start interactive schedule appointment
                    AppointmentController.interactiveScheduleAppointment(scanner);
                    pause();
                }
                case 2 -> {
                    clearScreen();
                    System.out.print("Enter the appointmentID: ");
                    String appointmentID = scanner.nextLine();

                    boolean removed = AppointmentController.cancelAppointment(appointmentID);
                    if(removed){
                        System.out.println("The appointment have been removed successfully.");
                    }else{
                        System.out.println("The appointment failed to be removed.");
                    }
                    pause();
                }
                case 3 -> {
                    clearScreen();
                    AppointmentController.displayAllAppointment();
                    System.out.print("\nEnter appointmentID: ");
                    String appointmentID = scanner.nextLine();

                    System.out.print("Enter the new status: ");
                    String status = scanner.nextLine();
                    boolean update = AppointmentController.updateAppointmentStatus(appointmentID, status);
                    if (update){
                        System.out.println("The appointment has been updated successfully.");
                    }else{
                        System.out.println("The appointment failed to update.");
                    }
                    pause();
                }
                case 4 -> {
                    //View appointments
                    clearScreen();
                    AppointmentController.displayAllAppointment();
                    pause();
                }
                default -> System.out.println("Invalid choice!");
                
            }
        }
        
    }

    // 5. MANAGE MEDICAL RECORDS
    private static void manageMedicalRecords() {
        while (true) {
            clearScreen();
            System.out.println(StringConstants.MEDICALRECORD_MENU);
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> {
                    showMainMenu();
                    //Back to Main Menu
                }
                case 1 -> {
                    // Add medical record
                    clearScreen();
                    System.out.print("Enter the patientID: ");
                    String patientID = scanner.nextLine();
                    String recordID = MedicalRecordController.createRecord(patientID);

                    // Add diagnosis
                    System.out.print("Enter the diagnosis: ");
                    String diagnosis = scanner.nextLine();
                    MedicalRecordController.addDiagnosis(recordID, diagnosis);

                    // Add prescription
                    System.out.print("Enter the prescription: ");
                    String prescription = scanner.nextLine();
                    MedicalRecordController.addPrescription(recordID, prescription);

                    // Display the recordID
                    System.out.println("Record ID: " + recordID);
                    System.out.println("Medical record added successfully.");
                }
                case 2 -> {
                    // Remove medical record
                    clearScreen();
                    System.out.print("Enter the record ID: ");
                    String recordID = scanner.nextLine();

                    boolean removed = MedicalRecordController.removeRecord(recordID);
                    if (removed) {
                        System.out.println("Medical record removed successfully.");
                    }else{
                        System.out.println("Record not found.");
                    }
                }
                case 3 -> {
                    // Update medical record details
                    clearScreen();
                    System.out.print("Enter the record ID: ");
                    String recordID = scanner.nextLine();

                    // Update diagnosis
                    System.out.print("Enter the new diagnosis: ");
                    String newDiagnosis = scanner.nextLine();
                    MedicalRecordController.addDiagnosis(recordID, newDiagnosis);

                    // Update prescription
                    System.out.print("Enter the new prescription: ");
                    String newPrescription = scanner.nextLine();
                    MedicalRecordController.addPrescription(recordID, newPrescription);

                    System.out.println("The record has been updated successfully.");
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
                PatientController.searchPatientsByName(name).forEach(
                        patient -> System.out.println("ID: " + patient.getPersonID() + ", Name: " + patient.getName()));
            }
            case 2 -> {
                System.out.print("Enter doctor name: ");
                String doctorName = scanner.nextLine();
                clearScreen();
                System.out.println("\n=== Search Results ===");
                DoctorController.searchDoctorsByName(doctorName).forEach(
                        doctor -> System.out.println("Dr. " + doctor.getName() + " - " + doctor.getSpecialization()));
            }
            case 3 -> {
                System.out.print("Enter department: ");
                String department = scanner.nextLine();
                clearScreen();
                System.out.println("\n=== Doctors by Department ===");
                DoctorController.getDoctorsByDepartment(department).forEach(
                        doctor -> System.out.println("Dr. " + doctor.getName() + " - " + doctor.getSpecialization()));
            }
            case 4 -> {
                System.out.print("Enter minimum years of experience: ");
                int yearsOfExperience = scanner.nextInt();
                scanner.nextLine();
                clearScreen();
                System.out.println("\n=== Doctors by Experience ===");
                DoctorController.getDoctorsByExperience(yearsOfExperience).forEach(doctor -> System.out
                        .println("Dr. " + doctor.getName() + " - " + doctor.getYearsOfExperience() + " years"));
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
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error clearing screen: " + e.getMessage());
        }
    }

    private static void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}