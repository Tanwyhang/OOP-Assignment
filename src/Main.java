import controllers.*;
import java.io.Console;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import models.Doctor;
import models.Nurse;
import models.Patient;
import utils.StringConstants;
import utils.StringUtils;
import utils.StringUtils.BorderColor;


public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static AdminController adminController;

    public enum DayOfWeek {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }

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
    private static boolean showLoginScreen() {
        clearScreen();
        Console console = System.console();
        System.out.println(StringConstants.LOGO + StringUtils.beautify("=== Hospital Management System Login ==="));
        String userID = console.readLine("Enter Admin ID: ");
        // use System.console.readPassword() to hide password input
        String password = new String(console.readPassword("Enter Password: "));

        
        if (adminController.login(userID, password)) {
            return true;
        } else {
            System.out.println(StringUtils.beautify(" CREDENTIALS DO NOT MATCH! ", BorderColor.RED));
            pause(false);
            showLoginScreen();
        }
        return false;
    }

    // -> MAIN MENU
    private static void showMainMenu() {
        while (true) {
            clearScreen();
            System.out.println(StringConstants.MAIN_MENU);
            int choice;
            while (true) {
                System.out.print("Choose an option: ");
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice >= 0 && choice <= 9) { // Valid choices are between 0 and 7 inclusive
                        break;
                    } else {
                        System.out.println(StringUtils.beautify("Error: Choice must be between 0-9. Please try again.", BorderColor.RED));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(StringUtils.beautify("Error: Invalid input. Please enter a valid number.", BorderColor.RED));
                }
            }

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
                    manageNurses();
                    break;
                case 5:
                    manageAppointments();
                    break;
                case 6: 
                    manageMedicalRecords(); 
                    break;
                case 7:
                    searchViewData();
                    break;
                case 8:
                    analyticsDashboard();
                    break;
                case 9:
                    changePassword();
                    return;
                case 0:
                    System.exit(0);
                default:
                    System.out.println(StringUtils.beautify("Invalid choice!", BorderColor.RED));
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
            System.out.print("Choose an option (or enter 0 to go back): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> managingRooms = false; // Exit to main menu
                case 1 -> {
                    while (true) {
                        clearScreen();
                        RoomController.displayAllRooms();
                        System.out.println(StringConstants.ROOM_TYPE_MENU);
                        System.out.print("Choose an option (or enter 0 to go back): ");
                        int roomTypeChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (roomTypeChoice == 0) break; // Back to "Manage Rooms"

                        String type;
                        switch (roomTypeChoice) {
                            case 1 -> type = "ICU";
                            case 2 -> type = "Ward";
                            case 3 -> type = "Emergency";
                            case 4 -> type = "Operation";
                            case 5 -> type = "Isolation";
                            default -> {
                                System.out.println(StringUtils.beautify("Invalid room type choice!", BorderColor.RED));
                                pause();
                                continue;
                            }
                        }
                        RoomController.addRoom(type);
                        System.out.println(StringUtils.beautify("Room added successfully!"));
                        pause();
                        break;
                    }
                }
                case 2 -> {
                    clearScreen();
                    RoomController.displayAllRooms();
                    System.out.print("Enter room ID to remove (or enter 0 to cancel): ");
                    String roomID = scanner.nextLine();
                    if (roomID.equals("0")) continue; // Back to "Manage Rooms"
                    if (RoomController.removeRoom(roomID)) {
                        System.out.println(StringUtils.beautify("Room removed successfully!"));
                    } else {
                        System.out.println(StringUtils.beautify("Room not found!", BorderColor.RED));
                    }
                    pause();
                }
                case 3 -> {
                    clearScreen();
                    RoomController.displayAllRooms();
                    System.out.print("Enter room ID (or enter 0 to cancel): ");
                    String roomID = scanner.nextLine();
                    if (roomID.equals("0")) continue; // Back to "Manage Rooms"
                    System.out.print("Assign new status:\n[1] Available\n[2] Occupied\nChoose an option (or enter 0 to cancel): ");
                    int status = scanner.nextInt();
                    if (status == 0) continue; // Back to "Manage Rooms"
                    switch (status) {
                        case 1 -> RoomController.updateRoomStatus(roomID, "available");
                        case 2 -> RoomController.updateRoomStatus(roomID, "unavailable");
                        default -> {
                            System.out.println(StringUtils.beautify("Invalid status choice!", BorderColor.RED));
                            continue;
                        }
                    }
                    System.out.println(StringUtils.beautify("Room status updated successfully!"));
                    pause();
                }
                default -> System.out.println(StringUtils.beautify("Invalid choice!", BorderColor.RED));
            }
        }
    }

    // 2. MANAGE PATIENTS
    private static void managePatients() {
        while (true) {
            clearScreen();
            System.out.println(StringConstants.PATIENT_MENU);
            System.out.print("Choose an option (or enter 0 to go back): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> showMainMenu(); // Exit to main menu
                case 1 -> {
                    clearScreen();
                    String name, address, phoneNumber;
                    int age;
                    char gender;

                    // prints sample input data
                    System.out.println(StringConstants.PATIENT_SAMPLE_DATA);

                    name = getValidInput("Enter patient name (or type '0' to abort): ", "Name");
                    if (name == null) continue; // Back to "Manage Patients"

                    age = getValidIntegerInput("Enter patient age (or type 'cancel' to abort): ", "Age");
                    if (age == -1) continue; // Back to "Manage Patients"

                    gender = getValidGenderInput("Enter patient gender (M/F) (or type '0' to abort): ");
                    if (gender == '\0') continue; // Back to "Manage Patients"

                    address = getValidInput("Enter patient address (or type '0' to abort): ", "Address");
                    if (address == null) continue; // Back to "Manage Patients"

                    phoneNumber = getValidInput("Enter patient phone number (or type '0' to abort): ", "Phone Number");
                    if (phoneNumber == null) continue; // Back to "Manage Patients"

                    PatientController.registerPatient(name, address, phoneNumber, gender, age);
                    System.out.println(StringUtils.beautify("Patient added successfully!"));
                    pause();
                }
                case 2 -> {
                    clearScreen();
                    PatientController.displayAllPatients();
                    System.out.print("Enter patient ID to remove (or type 'cancel' to abort): ");
                    String patientID = scanner.nextLine();
                    if (patientID.equalsIgnoreCase("cancel")) continue; // Back to "Manage Patients"
                    if (PatientController.dischargePatient(patientID)) {
                        System.out.println(StringUtils.beautify("Patient removed successfully!"));
                    } else {
                        System.out.println(StringUtils.beautify("Patient not found!", BorderColor.RED));
                    }
                    pause();
                }
                case 3 -> {
                    clearScreen();
                    PatientController.displayAllPatients();
                    System.out.print("Enter patient ID to update (or type 'cancel' to abort): ");
                    String patientID = scanner.nextLine();
                    if (patientID.equalsIgnoreCase("cancel")) continue; // Back to "Manage Patients"

                    String name = getValidInput("Enter new name (or type 'cancel' to abort): ", "Name");
                    if (name == null) continue; // Back to "Manage Patients"

                    int age = getValidIntegerInput("Enter new age (or type 'cancel' to abort): ", "Age");
                    if (age == -1) continue; // Back to "Manage Patients"

                    char gender = getValidGenderInput("Enter new gender (M/F) (or type 'cancel' to abort): ");
                    if (gender == '\0') continue; // Back to "Manage Patients"

                    String address = getValidInput("Enter new address (or type 'cancel' to abort): ", "Address");
                    if (address == null) continue; // Back to "Manage Patients"

                    String phoneNumber = getValidInput("Enter new phone number (or type 'cancel' to abort): ", "Phone Number");
                    if (phoneNumber == null) continue; // Back to "Manage Patients"

                    PatientController.updatePatientDetails(patientID, name, address, phoneNumber, gender, age);
                    System.out.println(StringUtils.beautify("Patient details updated successfully!"));
                    pause();
                }
                case 4 -> {
                    clearScreen();
                    PatientController.displayAllPatients();
                    System.out.print("Enter patient ID to view details (or type 'cancel' to abort): ");
                    String patientID = scanner.nextLine();
                    if (patientID.equalsIgnoreCase("cancel")) continue; // Back to "Manage Patients"
                    PatientController.viewPatientDetails(patientID);
                    pause();
                }
                default -> System.out.println(StringUtils.beautify("Invalid choice!", BorderColor.RED));
            }
        }
    }

    private static int getValidIntegerInput(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("cancel")) return -1; // Cancel operation
            try {
                int value = Integer.parseInt(input);
                if (value > 0) return value;
                System.out.println(StringUtils.beautify("Error: " + fieldName + " must be a positive number. Please try again.", BorderColor.RED));
            } catch (NumberFormatException e) {
                System.out.println(StringUtils.beautify("Error: Invalid input. Please enter a valid number.", BorderColor.RED));
            }
        }
    }

    private static char getValidGenderInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("cancel")) return '\0'; // Cancel operation
            if (input.equalsIgnoreCase("M") || input.equalsIgnoreCase("F")) return input.toUpperCase().charAt(0);
            System.out.println(StringUtils.beautify("Error: Gender must be 'M' or 'F'. Please try again.", BorderColor.RED));
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
                    // prints sample input data
                    System.out.println(StringConstants.DOCTOR_SAMPLE_DATA);
                    String name = null, address = null, phoneNumber = null, department = null, shift = null, specialization = null;
                    int age = 0, yearsOfExperience = 0;
                    char gender = 0;
                    boolean in_progress = true;

                    // Validate and get doctor name
                    while (in_progress) {
                        System.out.print("Enter doctor name (e.g., John Doe) or type 'cancel' to abort: ");
                        name = scanner.nextLine();
                        if (name.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (name.trim().isEmpty()) {
                            System.out.println("Error: Name cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Validate and get doctor age
                    while (in_progress) {
                        System.out.print("Enter doctor age (e.g., 35) or type 'cancel' to abort: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        try {
                            age = Integer.parseInt(input);
                            if (age > 0) {
                                break;
                            } else {
                                System.out.println("Error: Age must be a positive number. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid input. Please enter a valid age.");
                        }
                    }

                    // Validate and get doctor gender
                    while (in_progress) {
                        System.out.print("Enter doctor gender (M/F) or type 'cancel' to abort: ");
                        String genderInput = scanner.nextLine();
                        if (genderInput.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (genderInput.equalsIgnoreCase("M") || genderInput.equalsIgnoreCase("F")) {
                            gender = genderInput.toUpperCase().charAt(0);
                            break;
                        } else {
                            System.out.println("Error: Gender must be 'M' or 'F'. Please try again.");
                        }
                    }

                    // Validate and get doctor address
                    while (in_progress) {
                        System.out.print("Enter doctor address (e.g., 123 Main St, City) or type 'cancel' to abort: ");
                        address = scanner.nextLine();
                        if (address.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (address.trim().isEmpty()) {
                            System.out.println("Error: Address cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Validate and get doctor phone number
                    while (in_progress) {
                        System.out.print("Enter doctor phone number (e.g., 1234567890) or type 'cancel' to abort: ");
                        phoneNumber = scanner.nextLine();
                        if (phoneNumber.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (phoneNumber.trim().isEmpty() || !phoneNumber.matches("\\d+")) {
                            System.out.println("Error: Phone number must be numeric and cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Validate and get doctor department
                    while (in_progress) {
                        System.out.print("Enter doctor department (e.g., Cardiology) or type 'cancel' to abort: ");
                        department = scanner.nextLine();
                        if (department.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (department.trim().isEmpty()) {
                            System.out.println("Error: Department cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }


                    // Validate and get doctor shift
                    while (in_progress) {
                        System.out.print("Enter doctor shift (e.g., Morning, Evening) or type 'cancel' to abort: ");
                        shift = scanner.nextLine();
                        if (shift.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (shift.trim().isEmpty()) {
                            System.out.println("Error: Shift cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Validate and get doctor years of experience
                    while (in_progress) {
                        System.out.print("Enter doctor years of experience (e.g., 10) or type 'cancel' to abort: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        try {
                            yearsOfExperience = Integer.parseInt(input);
                            if (yearsOfExperience >= 0) {
                                break;
                            } else {
                                System.out.println("Error: Years of experience cannot be negative. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid input. Please enter a valid number.");
                        }
                    }

                    // Validate and get doctor specialization
                    while (in_progress) {
                        System.out.print("Enter doctor specialization (e.g., Surgeon) or type 'cancel' to abort: ");
                        specialization = scanner.nextLine();
                        if (specialization.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (specialization.trim().isEmpty()) {
                            System.out.println("Error: Specialization cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Add doctor using validated inputs
                    if (in_progress) {
                        DoctorController.hireDoctor(name, address, phoneNumber, gender, age, department, shift,
                            yearsOfExperience, specialization);
                        System.out.println("Doctor added successfully!");
                    }
                    pause();
                }
                // Remove Doctor
                case 2 -> {
                    clearScreen();
                    DoctorController.displayAllDoctors();
                    
                    String doctorID = getValidDoctorID("Enter doctor ID to remove (or type '0' to abort): ");
                    if (doctorID != null) {
                        if (DoctorController.removeDoctor(doctorID)) {
                            System.out.println("Doctor removed successfully!");
                        } else {
                            System.out.println("Doctor not found!");
                        }
                    } else {
                        System.out.println(StringUtils.beautify("Operation cancelled", BorderColor.RED));
                    }
                    pause();
                }
                // Update Doctor Details
                case 3 -> {
                    clearScreen();
                    DoctorController.displayAllDoctors();
                    System.out.println(StringConstants.DOCTOR_SAMPLE_DATA);

                    String doctorID = getValidInput("Enter doctor ID to update (or type '0' to abort): ", "Doctor ID");
                    if (doctorID == null) {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                        pause();
                        break;
                    }

                    // Update specialization
                    System.out.print("Enter new specialization (leave blank to skip, or type '0' to abort): ");
                    String specialization = scanner.nextLine();
                    if (specialization.equals("0")) {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                        pause();
                        break;
                    }
                    if (!specialization.isBlank()) {
                        DoctorController.updateSpecialization(doctorID, specialization);
                        System.out.println(StringUtils.beautify("Doctor specialization updated successfully!"));
                    } else {
                        System.out.println("Specialization update skipped.");
                    }

                    // Update department
                    System.out.print("Enter new department (leave blank to skip, or type '0' to abort): ");
                    String department = scanner.nextLine();
                    if (department.equals("0")) {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                        pause();
                        break;
                    }
                    if (!department.isBlank()) {
                        DoctorController.updateDepartment(doctorID, department);
                        System.out.println(StringUtils.beautify("Doctor department updated successfully!"));
                    } else {
                        System.out.println("Department update skipped.");
                    }

                    // Update shift
                    System.out.print("Enter new shift (leave blank to skip, or type '0' to abort): ");
                    String shift = scanner.nextLine();
                    if (shift.equals("0")) {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                        pause();
                        break;
                    }
                    if (!shift.isBlank()) {
                        DoctorController.updateShift(doctorID, shift);
                        System.out.println(StringUtils.beautify("Doctor shift updated successfully!"));
                    } else {
                        System.out.println("Shift update skipped.");
                    }

                    // Update years of experience
                    System.out.print("Enter new years of experience (leave blank to skip, or type '0' to abort): ");
                    String experienceInput = scanner.nextLine();
                    if (experienceInput.equals("0")) {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                        pause();
                        break;
                    }
                    if (!experienceInput.isBlank()) {
                        try {
                            int yearsOfExperience = Integer.parseInt(experienceInput);
                            DoctorController.updateExperience(doctorID, yearsOfExperience);
                            System.out.println(StringUtils.beautify("Doctor years of experience updated successfully!"));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input for years of experience. Update skipped.");
                        }
                    } else {
                        System.out.println("Years of experience update skipped.");
                    }

                    pause();
                }
                // View Doctors
                case 4 -> {
                    clearScreen();
                    DoctorController.displayAllDoctors();
                    
                    String doctorID = getValidDoctorID("Enter doctor ID to view details (or type '0' to abort): ");
                    if (doctorID != null) {
                        DoctorController.viewDoctorDetails(doctorID);
                    } else {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                    }
                    pause();
                }
            }
        }
    }

    // 4. MANAGE NURSES
    public static void manageNurses() {
        while (true) {
            clearScreen();
            System.out.println(StringConstants.NURSE_MENU);
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> {
                    showMainMenu();
                } // Exit to main menu
                // Add Nurse
                case 1 -> {
                    clearScreen();
                    // prints sample input data
                    System.out.println(StringConstants.NURSE_SAMPLE_DATA);
                    String name = null, address = null, phoneNumber = null, department = null, shift = null;
                    int age = 0, yearsOfExperience = 0;
                    char gender = 0;
                    boolean in_progress = true;

                    // Validate and get nurse name
                    while (in_progress) {
                        System.out.print("Enter nurse name (e.g., Jane Smith) or type 'cancel' to abort: ");
                        name = scanner.nextLine();
                        if (name.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (name.trim().isEmpty()) {
                            System.out.println("Error: Name cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Validate and get nurse age
                    while (in_progress) {
                        System.out.print("Enter nurse age (e.g., 30) or type 'cancel' to abort: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        try {
                            age = Integer.parseInt(input);
                            if (age > 0) {
                                break;
                            } else {
                                System.out.println("Error: Age must be a positive number. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid input. Please enter a valid age.");
                        }
                    }

                    // Validate and get nurse gender
                    while (in_progress) {
                        System.out.print("Enter nurse gender (M/F) or type 'cancel' to abort: ");
                        String genderInput = scanner.nextLine();
                        if (genderInput.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (genderInput.equalsIgnoreCase("M") || genderInput.equalsIgnoreCase("F")) {
                            gender = genderInput.toUpperCase().charAt(0);
                            break;
                        } else {
                            System.out.println("Error: Gender must be 'M' or 'F'. Please try again.");
                        }
                    }

                    // Validate and get nurse address
                    while (in_progress) {
                        System.out.print("Enter nurse address (e.g., 123 Main St, City) or type 'cancel' to abort: ");
                        address = scanner.nextLine();
                        if (address.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (address.trim().isEmpty()) {
                            System.out.println("Error: Address cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Validate and get nurse phone number
                    while (in_progress) {
                        System.out.print("Enter nurse phone number (e.g., 1234567890) or type 'cancel' to abort: ");
                        phoneNumber = scanner.nextLine();
                        if (phoneNumber.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (phoneNumber.trim().isEmpty() || !phoneNumber.matches("\\d+")) {
                            System.out.println("Error: Phone number must be numeric and cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Validate and get nurse department
                    while (in_progress) {
                        System.out.print("Enter nurse department (e.g., ICU) or type 'cancel' to abort: ");
                        department = scanner.nextLine();
                        if (department.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (department.trim().isEmpty()) {
                            System.out.println("Error: Department cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Validate and get nurse shift
                    while (in_progress) {
                        System.out.print("Enter nurse shift (e.g., Morning, Night) or type 'cancel' to abort: ");
                        shift = scanner.nextLine();
                        if (shift.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        if (shift.trim().isEmpty()) {
                            System.out.println("Error: Shift cannot be empty. Please try again.");
                        } else {
                            break;
                        }
                    }

                    // Validate and get nurse years of experience
                    while (in_progress) {
                        System.out.print("Enter nurse years of experience (e.g., 5) or type 'cancel' to abort: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("cancel")) {
                            System.out.println("Operation canceled.");
                            in_progress = false;
                            break;
                        }
                        try {
                            yearsOfExperience = Integer.parseInt(input);
                            if (yearsOfExperience >= 0) {
                                break;
                            } else {
                                System.out.println("Error: Years of experience cannot be negative. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid input. Please enter a valid number.");
                        }
                    }

                    // Add nurse using validated inputs
                    if (in_progress) {
                        NurseController.hireNurse(name, address, phoneNumber, gender, age, department, shift,
                            yearsOfExperience);
                        System.out.println("Nurse added successfully!");
                    }
                    pause();
                }
                // Remove Nurse
                case 2 -> {
                    clearScreen();
                    NurseController.displayAllNurses();
                    
                    String nurseID = getValidNurseID("Enter nurse ID to remove (or type '0' to abort): ");
                    if (nurseID != null) {
                        if (NurseController.removeNurse(nurseID)) {
                            System.out.println("Nurse removed successfully!");
                        } else {
                            System.out.println("Nurse not found!");
                        }
                    } else {
                        System.out.println(StringUtils.beautify("Operation cancelled", BorderColor.RED));
                    }
                    pause();
                }
                // Update Nurse Details
                case 3 -> {
                    clearScreen();
                    NurseController.displayAllNurses();
                    System.out.println(StringConstants.NURSE_SAMPLE_DATA);

                    String nurseID = getValidInput("Enter nurse ID to update (or type '0' to abort): ", "Nurse ID");
                    if (nurseID == null) {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                        pause();
                        break;
                    }

                    // Update department
                    System.out.print("Enter new department (leave blank to skip, or type '0' to abort): ");
                    String department = scanner.nextLine();
                    if (department.equals("0")) {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                        pause();
                        break;
                    }
                    if (!department.isBlank()) {
                        NurseController.updateDepartment(nurseID, department);
                        System.out.println(StringUtils.beautify("Nurse department updated successfully!"));
                    } else {
                        System.out.println("Department update skipped.");
                    }

                    // Update shift
                    System.out.print("Enter new shift (leave blank to skip, or type '0' to abort): ");
                    String shift = scanner.nextLine();
                    if (shift.equals("0")) {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                        pause();
                        break;
                    }
                    if (!shift.isBlank()) {
                        NurseController.updateShift(nurseID, shift);
                        System.out.println(StringUtils.beautify("Nurse shift updated successfully!"));
                    } else {
                        System.out.println("Shift update skipped.");
                    }

                    // Update years of experience
                    System.out.print("Enter new years of experience (leave blank to skip, or type '0' to abort): ");
                    String experienceInput = scanner.nextLine();
                    if (experienceInput.equals("0")) {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                        pause();
                        break;
                    }
                    if (!experienceInput.isBlank()) {
                        try {
                            int yearsOfExperience = Integer.parseInt(experienceInput);
                            NurseController.updateExperience(nurseID, yearsOfExperience);
                            System.out.println(StringUtils.beautify("Nurse years of experience updated successfully!"));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input for years of experience. Update skipped.");
                        }
                    } else {
                        System.out.println("Years of experience update skipped.");
                    }

                    pause();
                }
                // View Nurses
                case 4 -> {
                    clearScreen();
                    NurseController.displayAllNurses();
                    
                    String nurseID = getValidNurseID("\nEnter nurse ID to view details (or type '0' to abort): ");
                    if (nurseID != null) {
                        NurseController.viewNurseDetails(nurseID);
                    } else {
                        System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                    }
                    pause();
                }
            }
        }
    }

    // 5. MANAGE APPOINTMENT
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
                    AppointmentController.displayAllAppointment();
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

                    // prints sample input data 1 for Scheduled 2 for active
                    System.out.println(StringUtils.beautify("Status codes:\n1: Scheduled\n2: Active"));

                    String status_pass = null;
                    while (true) {
                        System.out.print("Enter the new status (0 to cancel): ");
                        try {
                            int status = scanner.nextInt(); 
                            scanner.nextLine(); // consume newline

                            switch (status) {
                                case 0 -> {
                                    System.out.println(StringUtils.beautify("Operation canceled.", BorderColor.RED));
                                    continue; // Back to "Manage Appointments"
                                }
                                case 1 -> {
                                    status_pass = "Scheduled";
                                    break;
                                }
                                case 2 -> {
                                    status_pass = "Active";
                                    break;
                                }
                                default -> {
                                    System.out.println("Invalid status choice! Please enter 0, 1 or 2");
                                    continue;
                                }
                            }
                            break; // Exit loop if valid input
                        } catch (Exception e) {
                            System.out.println("Invalid input! Please enter a number");
                            scanner.nextLine(); // Clear invalid input
                        }
                    }

                    if (status_pass != null) {
                        boolean update = AppointmentController.updateAppointmentStatus(appointmentID, status_pass);
                        if (update) {
                            System.out.println("The appointment has been updated successfully.");
                        } else {
                            System.out.println("The appointment failed to update.");
                        }
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

    // 6. MANAGE MEDICAL RECORDS
    private static void manageMedicalRecords() {
        while (true) {
            clearScreen();
            System.out.println(StringConstants.MEDICALRECORD_MENU);
            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (NumberFormatException e) {
                System.out.println(StringUtils.beautify("Invalid input! Please enter a number.", BorderColor.RED));
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            switch (choice) {
                case 0 -> showMainMenu(); // Back to Main Menu

                case 1 -> { // Add medical record
                    clearScreen();
                    PatientController.displayAllPatients();
                    // Get patient ID
                    String patientID;
                    while (true) {
                        System.out.print("Enter the patient ID (0 to cancel): ");
                        patientID = scanner.nextLine();
                        if (patientID.equals("0")) {
                            System.out.println("Operation cancelled.");
                            break;
                        }
                        if (PatientController.isPatientExist(patientID)) {
                            break;
                        }
                        System.out.println(StringUtils.beautify("Patient not found! Please enter a valid ID.", BorderColor.RED));
                    }
                    if (patientID.equals("0")) continue;

                    String recordID = MedicalRecordController.createRecord(patientID);

                    // Add diagnosis
                    String diagnosis;
                    while (true) {
                        System.out.print("Enter the diagnosis (0 to cancel): ");
                        diagnosis = scanner.nextLine();
                        if (diagnosis.equals("0")) {
                            System.out.println("Operation cancelled.");
                            break;
                        }
                        if (!diagnosis.trim().isEmpty()) {
                            break;
                        }
                        System.out.println(StringUtils.beautify("Diagnosis cannot be empty!", BorderColor.RED));
                    }
                    if (diagnosis.equals("0")) continue;
                    MedicalRecordController.addDiagnosis(recordID, diagnosis);

                    // Add prescription
                    String prescription;
                    while (true) {
                        System.out.print("Enter the prescription (0 to cancel): ");
                        prescription = scanner.nextLine();
                        if (prescription.equals("0")) {
                            System.out.println("Operation cancelled.");
                            break;
                        }
                        if (!prescription.trim().isEmpty()) {
                            break;
                        }
                        System.out.println(StringUtils.beautify("Prescription cannot be empty!", BorderColor.RED));
                    }
                    if (prescription.equals("0")) continue;
                    MedicalRecordController.addPrescription(recordID, prescription);

                    System.out.println(StringUtils.beautify("Medical record added successfully! Record ID: " + recordID));
                    pause();
                }

                case 2 -> { // Remove medical record
                    clearScreen();
                    // Display all patients first
                    PatientController.displayAllPatients();
                    
                    // Get and validate patient ID
                    String patientID;
                    while (true) {
                        System.out.print("\nEnter patient ID to view their medical records (0 to cancel): ");
                        patientID = scanner.nextLine();
                        
                        if (patientID.equals("0")) {
                            System.out.println("Operation cancelled.");
                            break;
                        }
                        
                        if (PatientController.isPatientExist(patientID)) {
                            // Display medical records for the selected patient
                            System.out.println("\nMedical Records for Patient ID: " + patientID);
                            MedicalRecordController.displayRecords(patientID);
                            
                            // Get record ID for removal
                            System.out.print("\nEnter the record ID to remove (0 to cancel): ");
                            String recordID = scanner.nextLine();
                            
                            if (recordID.equals("0")) {
                                System.out.println("Operation cancelled.");
                                break;
                            }
                            
                            if (MedicalRecordController.removeRecord(recordID)) {
                                System.out.println(StringUtils.beautify("Medical record removed successfully!"));
                                break;
                            } else {
                                System.out.println(StringUtils.beautify("Record not found!", BorderColor.RED));
                            }
                            break;
                        }
                        System.out.println(StringUtils.beautify("Patient not found! Please enter a valid ID.", BorderColor.RED));
                    }
                    pause();
                }

                case 3 -> { // Update medical record
                    clearScreen();
                    PatientController.displayAllPatients();
                    
                    // Get and validate patient ID first
                    String patientID;
                    while (true) {
                        System.out.print("\nEnter patient ID to view their medical records (0 to cancel): ");
                        patientID = scanner.nextLine();
                        
                        if (patientID.equals("0")) {
                            System.out.println("Operation cancelled.");
                            break;
                        }
                        
                        if (PatientController.isPatientExist(patientID)) {
                            // Display medical records for the selected patient
                            System.out.println("\nMedical Records for Patient ID: " + patientID);
                            MedicalRecordController.displayRecords(patientID);
                            
                            // Get and validate record ID
                            while (true) {
                                System.out.print("\nEnter the record ID to update (0 to cancel): ");
                                String recordID = scanner.nextLine();
                                if (recordID.equals("0")) {
                                    System.out.println("Operation cancelled.");
                                    break;
                                }
                                if (!MedicalRecordController.recordExists(recordID)) {
                                    System.out.println(StringUtils.beautify("Record not found!", BorderColor.RED));
                                    continue;
                                }

                                // Update diagnosis
                                String newDiagnosis;
                                while (true) {
                                    System.out.print("Enter the new diagnosis (0 to cancel): ");
                                    newDiagnosis = scanner.nextLine();
                                    if (newDiagnosis.equals("0")) {
                                        System.out.println("Operation cancelled.");
                                        break;
                                    }
                                    if (!newDiagnosis.trim().isEmpty()) {
                                        break;
                                    }
                                    System.out.println(StringUtils.beautify("Diagnosis cannot be empty!", BorderColor.RED));
                                }
                                if (newDiagnosis.equals("0")) continue;
                                MedicalRecordController.addDiagnosis(recordID, newDiagnosis);

                                // Update prescription
                                String newPrescription;
                                while (true) {
                                    System.out.print("Enter the new prescription (0 to cancel): ");
                                    newPrescription = scanner.nextLine();
                                    if (newPrescription.equals("0")) {
                                        System.out.println("Operation cancelled.");
                                        break;
                                    }
                                    if (!newPrescription.trim().isEmpty()) {
                                        break;
                                    }
                                    System.out.println(StringUtils.beautify("Prescription cannot be empty!", BorderColor.RED));
                                }
                                if (newPrescription.equals("0")) continue;
                                MedicalRecordController.addPrescription(recordID, newPrescription);

                                // update treatment
                                String newTreatment;
                                while (true) {
                                    System.out.print("Enter the new treatment (0 to cancel): ");
                                    newTreatment = scanner.nextLine();
                                    if (newTreatment.equals("0")) {
                                        System.out.println("Operation cancelled.");
                                        break;
                                    }
                                    if (!newTreatment.trim().isEmpty()) {
                                        break;
                                    }
                                    System.out.println(StringUtils.beautify("Treatment cannot be empty!", BorderColor.RED));
                                }
                                if (newTreatment.equals("0")) continue;
                                MedicalRecordController.addTreatment(recordID, newTreatment);

                                System.out.println(StringUtils.beautify("Medical record updated successfully!"));
                                break;
                            }
                            break;
                        }
                        System.out.println(StringUtils.beautify("Patient not found! Please enter a valid ID.", BorderColor.RED));
                    }
                    pause();
                }

                case 4 -> { // View medical records
                    clearScreen();
                    PatientController.displayAllPatients();
                    while (true) {
                        System.out.print("Enter patient ID to view medical records (0 to cancel): ");
                        String patientID = scanner.nextLine();
                        if (patientID.equals("0")) {
                            System.out.println("Operation cancelled.");
                            break;
                        }
                        if (PatientController.isPatientExist(patientID)) {
                            MedicalRecordController.displayRecords(patientID);
                            break;
                        }
                        System.out.println(StringUtils.beautify("Patient not found!", BorderColor.RED));
                    }
                    pause();
                }

                default -> System.out.println(StringUtils.beautify("Invalid choice!", BorderColor.RED));
            }
        }
    }

    // 7. SEARCH/VIEW DATA
    private static void searchViewData() {
        while (true) {
            clearScreen();
            System.out.println(StringConstants.SEARCH_VIEW_MENU);
            System.out.print("Choose an option (0 to return to main menu): ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } catch (Exception e) {
                System.out.println(StringUtils.beautify("Invalid input! Please enter a number.", BorderColor.RED));
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> searchPatients();
                case 2 -> searchDoctors(); 
                case 3 -> searchNurses();
                case 4 -> searchAppointments();
                case 5 -> searchRooms();
                default -> {System.out.println(StringUtils.beautify("Invalid choice!", BorderColor.RED));
            }
            }
        }
    }

    private static void searchPatients() {

        while (true) {
            clearScreen();
            System.out.println(StringUtils.beautify("=== Search Patients ==="));
            System.out.println(StringUtils.beautify("[1] Search by Name\n[2] Search by Address\n[3] Search by Gender\n[4] Search by Age Range\n[0] Back"));

            System.out.print("Choose search criteria: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(StringUtils.beautify("Invalid input!", BorderColor.RED));
                scanner.nextLine();
                return;
            }

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    System.out.print("\nEnter patient name: ");
                    String name = scanner.nextLine();
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Patients with name \"" + name + "\" ==="));
                    List<Patient> results = PatientController.searchPatientsByName(name);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println(StringUtils.beautify("No patients found with that name.", BorderColor.RED));
                    }
                    pause();
                }
                case 2 -> {
                        System.out.print("\nEnter patient address: ");
                        String address = scanner.nextLine();
                        clearScreen(); 
                        System.out.println(StringUtils.beautify("=== Patients with address \"" + address + "\" ==="));
                        List<Patient> results = PatientController.searchPatientsByAddress(address);
                        if (!results.isEmpty()) {
                            results.forEach(System.out::println);
                        } else {
                            System.out.println(StringUtils.beautify("No patients found at that address.", BorderColor.RED));
                        }
                        pause();
                }
                case 3 -> {
                    System.out.println("\nSelect gender:");
                    System.out.println("[1] Male (M)");
                    System.out.println("[2] Female (F)");
                    System.out.print("Choice: ");
                    int genderChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    char gender = switch(genderChoice) {
                        case 1 -> 'M';
                        case 2 -> 'F';
                        default -> {
                            System.out.println(StringUtils.beautify("Invalid choice!", BorderColor.RED));
                            yield '\0';
                        }
                    };
                    
                    if (gender != '\0') {
                        clearScreen();
                        System.out.println(StringUtils.beautify("=== Patients with Gender \"" + gender + "\" ==="));
                        List<Patient> results = PatientController.searchPatientsByGender(gender);
                        if (!results.isEmpty()) {
                            results.forEach(System.out::println);
                            pause();
                        } else {
                            System.out.println("No patients found of that gender.");
                        }
                    }
                    pause();
                }
                case 4 -> {
                    System.out.println("\nSelect age range:");
                    System.out.println("[1] Children (0-12)");
                    System.out.println("[2] Teenagers (13-19)");
                    System.out.println("[3] Young Adults (20-39)");
                    System.out.println("[4] Middle-aged Adults (40-59)");
                    System.out.println("[5] Seniors (60+)");
                    System.out.print("Choice: ");
                    int ageChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    int[] ageRange = switch(ageChoice) {
                        case 1 -> new int[]{0, 12};
                        case 2 -> new int[]{13, 19};
                        case 3 -> new int[]{20, 39};
                        case 4 -> new int[]{40, 59};
                        case 5 -> new int[]{60, 150};
                        default -> null;
                    };
                    
                    if (ageRange != null) {
                        clearScreen();
                        System.out.println(StringUtils.beautify("=== Patients aged " + ageRange[0] + "-" + ageRange[1] + " ==="));
                        List<Patient> result = PatientController.searchPatientsByAgeRange(ageRange[0], ageRange[1]);
                        if (!result.isEmpty()) {
                            result.forEach(System.out::println);
                        } else {
                            System.out.println("No patients found in that age range.");
                        }
                    }
                    pause();
                }
            }
        }
    }

    private static void searchDoctors() {
        clearScreen();
        System.out.println(StringUtils.beautify("=== Search Doctors ==="));
        System.out.println(StringUtils.beautify("[1] Search by Name\n[2] Search by Department\n[3] Search by Gender\n[4] Search by Age Range\n[5] Search by Experience\n[6] Search by Shift\n[0] Back"));

        System.out.print("\nChoose search criteria: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(StringUtils.beautify("Invalid input!", BorderColor.RED));
            scanner.nextLine();
            return;
        }

        switch (choice) {
            case 0 -> {
                return;
            }
            case 1 -> {
                System.out.print("\nEnter doctor name: ");
                String name = scanner.nextLine();
                clearScreen();
                System.out.println(StringUtils.beautify("=== Doctors named \"" + name + "\" ==="));
                List<Doctor> results = DoctorController.searchDoctorsByName(name);
                if (!results.isEmpty()) {
                    results.forEach(System.out::println);
                } else {
                    System.out.println("No doctors found with that name.");
                }
                pause();
            }
            case 2 -> {
                System.out.println("\nSelect department:");
                System.out.println("[1] Surgery");
                System.out.println("[2] Cardiology");
                System.out.println("[3] Pediatrics");
                System.out.println("[4] Neurology");
                System.out.println("[5] Orthopedics");
                System.out.print("Choice: ");
                int deptChoice = scanner.nextInt();
                scanner.nextLine();
                
                String department = switch(deptChoice) {
                    case 1 -> "Surgery";
                    case 2 -> "Cardiology";
                    case 3 -> "Pediatrics";
                    case 4 -> "Neurology";
                    case 5 -> "Orthopedics";
                    default -> null;
                };
                
                if (department != null) {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Doctors in Department \"" + department + "\" ==="));
                    List<Doctor> results = DoctorController.getDoctorsByDepartment(department);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println("No doctors found in that department.");
                    }
                }
                pause();
            }
            case 3 -> {
                System.out.println("\nSelect gender:");
                System.out.println("[1] Male (M)");
                System.out.println("[2] Female (F)");
                System.out.print("Choice: ");
                int genderChoice = scanner.nextInt();
                scanner.nextLine();
                
                char gender = switch(genderChoice) {
                    case 1 -> 'M';
                    case 2 -> 'F';
                    default -> '\0';
                };
                
                if (gender != '\0') {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Doctors with Gender \"" + gender + "\" ==="));
                    List<Doctor> results = DoctorController.searchDoctorsByGender(gender);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println("No doctors found of that gender.");
                    }
                }
                pause();
            }
            case 4 -> {
                System.out.println("\nSelect age range:");
                System.out.println("[1] Young (25-35)");
                System.out.println("[2] Middle-aged (36-50)");
                System.out.println("[3] Senior (51+)");
                System.out.print("Choice: ");
                int ageChoice = scanner.nextInt();
                scanner.nextLine();
                
                int[] ageRange = switch(ageChoice) {
                    case 1 -> new int[]{25, 35};
                    case 2 -> new int[]{36, 50};
                    case 3 -> new int[]{51, 100};
                    default -> null;
                };
                
                if (ageRange != null) {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Doctors aged " + ageRange[0] + "-" + ageRange[1] + " ==="));
                    List<Doctor> results = DoctorController.searchDoctorsByAgeRange(ageRange[0], ageRange[1]);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println("No doctors found in that age range.");
                    }
                }
                pause();
            }
            case 5 -> {
                System.out.println("\nSelect experience level:");
                System.out.println("[1] Junior (0-5 years)");
                System.out.println("[2] Intermediate (6-10 years)");
                System.out.println("[3] Senior (11+ years)");
                System.out.print("Choice: ");
                int expChoice = scanner.nextInt();
                scanner.nextLine();
                
                int experience = switch(expChoice) {
                    case 1 -> 0;
                    case 2 -> 6;
                    case 3 -> 11;
                    default -> -1;
                };
                
                if (experience >= 0) {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Doctors with " + experience + "+ years of experience ==="));
                    List<Doctor> results = DoctorController.getDoctorsByExperience(experience);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println("No doctors found with that experience level.");
                    }
                    pause();
                }
            }
            case 6 -> {
                System.out.println("\nSelect shift:");
                System.out.println("[1] Morning");
                System.out.println("[2] Evening");
                System.out.print("Choice: ");
                int shiftChoice = scanner.nextInt();
                scanner.nextLine();
                
                String shift = switch(shiftChoice) {
                    case 1 -> "Morning";
                    case 2 -> "Evening";
                    default -> null;
                };
                
                if (shift != null) {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Doctors in " + shift + " shift ==="));
                    List<Doctor> results = DoctorController.getDoctorsByShift(shift);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println("No doctors found in that shift.");
                    }
                    pause();
                }
            }
        }
    }

    private static void searchNurses() {
        clearScreen();
        System.out.println(StringUtils.beautify("=== Search Nurses ==="));
        System.out.println(StringUtils.beautify("[1] Search by Name\n[2] Search by Department\n[3] Search by Gender\n[4] Search by Age Range\n[5] Search by Experience\n[6] Search by Shift\n[0] Back"));

        System.out.print("\nChoose search criteria: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(StringUtils.beautify("Invalid input!", BorderColor.RED));
            scanner.nextLine();
            return;
        }

        switch (choice) {
            case 0 -> {
                return;
            }
            case 1 -> {
                System.out.print("\nEnter nurse name: ");
                String name = scanner.nextLine();
                clearScreen();
                System.out.println(StringUtils.beautify("=== Nurses named \"" + name + "\" ==="));
                List<Nurse> results = NurseController.searchNursesByName(name);
                if (!results.isEmpty()) {
                    results.forEach(System.out::println);
                    pause();
                } else {
                    System.out.println("No nurses found with that name.");
                }
                pause();
            }

            case 2 -> {
                System.out.println("\nSelect department:");
                System.out.println("[1] Cardiology");
                System.out.println("[2] Neurology");
                System.out.println("[3] Pediatrics");
                System.out.println("[4] Oncology");
                System.out.println("[5] Dermatology");
                System.out.println("[6] Gastroenterology");
                System.out.println("[7] Ophthalmology");
                System.out.println("[8] Pulmonology");
                System.out.println("[9] Endocrinology");
                System.out.println("[10] Rheumatology");
                System.out.print("Choice: ");
                int deptChoice = scanner.nextInt();
                scanner.nextLine();
                    
                String department = switch(deptChoice) {
                    case 1 -> "Cardiology";
                    case 2 -> "Neurology"; 
                    case 3 -> "Pediatrics";
                    case 4 -> "Oncology";
                    case 5 -> "Dermatology";
                    case 6 -> "Gastroenterology";
                    case 7 -> "Ophthalmology";
                    case 8 -> "Pulmonology";
                    case 9 -> "Endocrinology";
                    case 10 -> "Rheumatology";
                    default -> null;
                };
                    
                if (department != null) {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Nurses in Department \"" + department + "\" ==="));
                    List<Nurse> results = NurseController.getNursesByDepartment(department);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println("No nurses found in that department.");
                    }

                    pause();
                }
            }

            case 3 -> {
                System.out.println("\nSelect gender:");
                System.out.println("[1] Male (M)");
                System.out.println("[2] Female (F)");
                System.out.print("Choice: ");
                int genderChoice = scanner.nextInt();
                scanner.nextLine();
                
                char gender = switch(genderChoice) {
                    case 1 -> 'M';
                    case 2 -> 'F';
                    default -> '\0';
                };
                
                if (gender != '\0') {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Nurses with Gender \"" + gender + "\" ==="));
                    List<Nurse> results = NurseController.searchNursesByGender(gender);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println("No nurses found of that gender.");
                    }
                    pause();
                }
            }
            
            case 4 -> {
                System.out.println("\nSelect age range:");
                System.out.println("[1] Young (20-30)");
                System.out.println("[2] Middle-aged (31-45)");
                System.out.println("[3] Senior (46+)");
                System.out.print("Choice: ");
                int ageChoice = scanner.nextInt();
                scanner.nextLine();
                
                int[] ageRange = switch(ageChoice) {
                    case 1 -> new int[]{20, 30};
                    case 2 -> new int[]{31, 45};
                    case 3 -> new int[]{46, 100};
                    default -> null;
                };
                
                if (ageRange != null) {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Nurses aged " + ageRange[0] + "-" + ageRange[1] + " ==="));
                    List<Nurse> results = NurseController.searchNursesByAgeRange(ageRange[0], ageRange[1]);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println("No nurses found in that age range.");
                    }
                    pause();
                }
            }
            case 5 -> {
                System.out.println("\nSelect experience level:");
                System.out.println("[1] Junior (0-3 years)");
                System.out.println("[2] Intermediate (4-7 years)");
                System.out.println("[3] Senior (8+ years)");
                System.out.print("Choice: ");
                int expChoice = scanner.nextInt();
                scanner.nextLine();
                
                int experience = switch(expChoice) {
                    case 1 -> 0;
                    case 2 -> 4;
                    case 3 -> 8;
                    default -> -1;
                };
                
                if (experience >= 0) {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Nurses with " + experience + "+ years of experience ==="));
                    List<Nurse> results = NurseController.getNursesByExperience(experience);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                        
                    } else {
                        System.out.println("No nurses found with that experience level.");
                    }
                    pause();
                }
            }
            case 6 -> {
                System.out.println("\nSelect shift:");
                System.out.println("[1] Morning");
                System.out.println("[2] Evening");
                System.out.println("[3] Night");
                System.out.print("Choice: ");
                int shiftChoice = scanner.nextInt();
                scanner.nextLine();
                
                String shift = switch(shiftChoice) {
                    case 1 -> "Morning";
                    case 2 -> "Evening";
                    case 3 -> "Night";
                    default -> null;
                };
                
                if (shift != null) {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Nurses in " + shift + " shift ==="));
                    List<Nurse> results = NurseController.getNursesByShift(shift);
                    if (!results.isEmpty()) {
                        results.forEach(System.out::println);
                    } else {
                        System.out.println("No nurses found in that shift.");
                    }
                    pause();
                }
            }
            default -> System.out.println(StringUtils.beautify("Invalid choice!", BorderColor.RED));
        }
    }

    private static void searchAppointments() {
        clearScreen();
        System.out.println(StringUtils.beautify("=== Search Appointments ==="));
        System.out.println(StringUtils.beautify("[1] Search by status\n[2] Search by date\n[0] Back"));

        System.out.print("Choose search criteria: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } catch (Exception e) {
            System.out.println(StringUtils.beautify("Invalid input!", BorderColor.RED));
            scanner.nextLine();
            return;
        }

        switch (choice) {
            case 0 -> {
                return;
            }
            case 1 -> {
                System.out.println("\nSelect appointment status:");
                System.out.println("[1] Active");
                System.out.println("[2] Scheduled");
                System.out.print("Choice: ");
                int statusChoice = scanner.nextInt();
                scanner.nextLine();
                
                String status = switch(statusChoice) {
                    case 1 -> "Active";
                    case 2 -> "Scheduled";
                    default -> null;
                };
                
                if (status != null) {
                    clearScreen();
                    System.out.println(StringUtils.beautify("=== Appointments with Status \"" + status + "\" ==="));
                    AppointmentController.searchAppointmentsByStatus(status);
                } else {
                    System.out.println(StringUtils.beautify("Invalid status choice!", BorderColor.RED));
                }
                pause();
            }
            case 2 -> {
                System.out.println("\nSelect date range:");
                System.out.println("[1] Today");
                System.out.println("[2] This Week");
                System.out.println("[3] This Month");
                System.out.print("Choice: ");
                int dateChoice = scanner.nextInt();
                scanner.nextLine();
                
                LocalDate today = LocalDate.now();
                LocalDate startDate;
                LocalDate endDate;

                switch (dateChoice) {
                    case 1 -> { // Today
                        startDate = today;
                        endDate = today;
                        clearScreen();
                        System.out.println(StringUtils.beautify("=== Appointments for Today ==="));
                        AppointmentController.searchAppointmentsByDateRange(startDate, endDate);
                        pause();
                    }
                    case 2 -> { // This week
                        startDate = today;
                        endDate = today.plusDays(7);
                        clearScreen();
                        System.out.println(StringUtils.beautify("=== Appointments for This Week ==="));
                        AppointmentController.searchAppointmentsByDateRange(startDate, endDate); 
                        pause();
                    }
                    case 3 -> { // This month 
                        startDate = today;
                        endDate = today.plusMonths(1);
                        clearScreen();
                        System.out.println(StringUtils.beautify("=== Appointments for This Month ==="));
                        AppointmentController.searchAppointmentsByDateRange(startDate, endDate);
                        pause();
                    }
                    default -> System.out.println(StringUtils.beautify("Invalid date range choice!", BorderColor.RED));
                }
            }
            default -> System.out.println(StringUtils.beautify("Invalid choice!", BorderColor.RED));
        }
    }

    private static void searchRooms() {
        clearScreen();
        System.out.println(StringUtils.beautify("=== Search Rooms ==="));
        System.out.println(StringUtils.beautify("[1] Search by room type\n[2] Search by status\n[0] Back"));

        System.out.print("Choose search criteria: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } catch (Exception e) {
            System.out.println(StringUtils.beautify("Invalid input!", BorderColor.RED));
            scanner.nextLine();
            return;
        }

        switch (choice) {
            case 0 -> {
            return;
            }
            case 1 -> {
            System.out.println("\nSelect room type:");
            System.out.println("[1] ICU");
            System.out.println("[2] Ward");
            System.out.println("[3] Emergency");
            System.out.println("[4] Operation");
            System.out.println("[5] Isolation");
            System.out.print("Choice: ");
            int roomChoice = scanner.nextInt();
            scanner.nextLine();
            
            String roomType = switch(roomChoice) {
                case 1 -> "ICU";
                case 2 -> "Ward";
                case 3 -> "Emergency";
                case 4 -> "Operation";
                case 5 -> "Isolation";
                default -> null;
            };
            
            if (roomType != null) {
                clearScreen();
                System.out.println(StringUtils.beautify("=== Rooms of Type \"" + roomType + "\" ==="));
                RoomController.searchRoomsByType(roomType);
            } else {
                System.out.println(StringUtils.beautify("Invalid room type choice!", BorderColor.RED));
            }
            pause();
            }
            case 2 -> {
            System.out.println("\nSelect status:");
            System.out.println("[1] Available");
            System.out.println("[2] Occupied");
            System.out.print("Choice: ");
            int statusChoice = scanner.nextInt();
            scanner.nextLine();
            
            String status = switch(statusChoice) {
                case 1 -> "Available";
                case 2 -> "Occupied";
                default -> null;
            };
            
            if (status != null) {
                clearScreen();
                System.out.println(StringUtils.beautify("=== Rooms with Status \"" + status + "\" ==="));
                RoomController.searchRoomsByStatus(status);
                pause();
            } else {
                System.out.println(StringUtils.beautify("Invalid status choice!", BorderColor.RED));
                pause(false);
            }
            }
            default -> System.out.println(StringUtils.beautify("Invalid choice!", BorderColor.RED));
        }
    }

    // 8. ANALYTICS DASHBOARD
    private static void analyticsDashboard() {
        
        // Calculate general statistics
        int totalPatients = new PatientController().getAll().size();
        int activeAppointments = AppointmentController.getActiveAppointments().size();
        int totalRooms = new RoomController().getAll().size(); 
        int availableRooms = RoomController.getAvailableRooms().size();
        int availablePercentage = (totalRooms > 0) ? (availableRooms * 100 / totalRooms) : 0;
        // get doctor count
        int doctorsOnDuty = new DoctorController().getAll().size();
        int nursesOnDuty = new NurseController().getAll().size();
        int totalStaff = doctorsOnDuty + nursesOnDuty;

        clearScreen();

        // Dashboard header
        System.out.println(StringConstants.LOGO_2);
        System.out.println((StringConstants.ANALYTICS_LOGO));

        // Display general statistics
        System.out.println("\n\n=== GENERAL STATISTICS ===");

        StringBuilder stats = new StringBuilder();
        stats.append(String.format("Total Patients       : %d\n", totalPatients))
             .append(String.format("Active Appointments  : %d\n", activeAppointments))
             .append(String.format("Available Rooms      : %d/%d (%d%%)\n", availableRooms, totalRooms, availablePercentage))
             .append(String.format("Staff on Duty        : %d (%d Doctors, %d Nurses)", totalStaff, doctorsOnDuty, nursesOnDuty));

        System.out.println(StringUtils.beautify(stats.toString()));

        // Room utilization 
        System.out.println("\n\n=== ROOM UTILIZATION ===");
        StringBuilder sb = new StringBuilder();
        
        // ICU rooms
        int totalICURooms = RoomController.getRoomsByType("ICU").size();
        int occupiedICURooms = RoomController.getOccupiedRoomsByType("ICU").size();
        int icuOccupancyRate = totalICURooms > 0 ? (occupiedICURooms * 100 / totalICURooms) : 0;
        sb.append(generateDemographicBar("ICU      ", icuOccupancyRate));

        // Ward rooms  
        int totalWardRooms = RoomController.getRoomsByType("Ward").size();
        int occupiedWardRooms = RoomController.getOccupiedRoomsByType("Ward").size();
        int wardOccupancyRate = totalWardRooms > 0 ? (occupiedWardRooms * 100 / totalWardRooms) : 0;
        sb.append(generateDemographicBar("Ward     ", wardOccupancyRate));

        // Emergency rooms
        int totalEmergencyRooms = RoomController.getRoomsByType("Emergency").size();
        int occupiedEmergencyRooms = RoomController.getOccupiedRoomsByType("Emergency").size(); 
        int emergencyOccupancyRate = totalEmergencyRooms > 0 ? (occupiedEmergencyRooms * 100 / totalEmergencyRooms) : 0;
        sb.append(generateDemographicBar("Emergency", emergencyOccupancyRate));

        // Operation rooms
        int totalOperationRooms = RoomController.getRoomsByType("Operation").size();
        int occupiedOperationRooms = RoomController.getOccupiedRoomsByType("Operation").size();
        int operationOccupancyRate = totalOperationRooms > 0 ? (occupiedOperationRooms * 100 / totalOperationRooms) : 0;
        sb.append(generateDemographicBar("Operation", operationOccupancyRate));

        // Isolation rooms
        int totalIsolationRooms = RoomController.getRoomsByType("Isolation").size();
        int occupiedIsolationRooms = RoomController.getOccupiedRoomsByType("Isolation").size();
        int isolationOccupancyRate = totalIsolationRooms > 0 ? (occupiedIsolationRooms * 100 / totalIsolationRooms) : 0;
        sb.append(generateDemographicBar("Isolation", isolationOccupancyRate));

        System.out.println(StringUtils.beautify(sb.toString()));

        System.out.println();

        // Patient demographics
        System.out.println("\n\n=== PATIENT AGE DISTRIBUTION ===");

        
        int childrenCount = PatientController.searchPatientsByAgeRange(0, 12).size();
        int teenagersCount = PatientController.searchPatientsByAgeRange(13, 19).size();
        int adultsCount = PatientController.searchPatientsByAgeRange(20, 59).size();
        int seniorsCount = PatientController.searchPatientsByAgeRange(60, 150).size();
        
        int childrenPercentage = totalPatients > 0 ? (childrenCount * 100 / totalPatients) : 0;
        int teenagersPercentage = totalPatients > 0 ? (teenagersCount * 100 / totalPatients) : 0;
        int adultsPercentage = totalPatients > 0 ? (adultsCount * 100 / totalPatients) : 0;
        int seniorsPercentage = totalPatients > 0 ? (seniorsCount * 100 / totalPatients) : 0;
        sb.setLength(0); // Clear the StringBuilder
        sb.append(generateDemographicBar("Children ", childrenPercentage))
            .append(generateDemographicBar("Teenagers", teenagersPercentage))
            .append(generateDemographicBar("Adults   ", adultsPercentage)) 
            .append(generateDemographicBar("Seniors  ", seniorsPercentage));

        System.out.println(StringUtils.beautify(sb.toString()));
        
        System.out.println("\n\n===Gender Distribution===");
        int maleCount = PatientController.searchPatientsByGender('M').size();
        int femaleCount = PatientController.searchPatientsByGender('F').size();
        
        int malePercentage = totalPatients > 0 ? (maleCount * 100 / totalPatients) : 0;
        int femalePercentage = totalPatients > 0 ? (femaleCount * 100 / totalPatients) : 0;
        
        sb.setLength(0); // Clear the StringBuilder
        sb.append(generateDemographicBar("Male    ", malePercentage));
        sb.append(generateDemographicBar("Female  ", femalePercentage));
        System.out.println(StringUtils.beautify(sb.toString()));

        // Appointment trends
        System.out.println(("\n\n=== APPOINTMENT TRENDS ==="));
        System.out.println("Weekly Distribution:");
        
        String[] days = {DayOfWeek.SUNDAY.name(), DayOfWeek.MONDAY.name(), DayOfWeek.TUESDAY.name(), 
            DayOfWeek.WEDNESDAY.name(), DayOfWeek.THURSDAY.name(), DayOfWeek.FRIDAY.name(), 
            DayOfWeek.SATURDAY.name()};
        int[] appointmentsByDay = new int[7];
        int maxAppointments = 0;
        String maxAppointmentDay = "";
        
        // Get max appointments for scaling
        for (int i = 0; i < days.length; i++) {
            appointmentsByDay[i] = AppointmentController.getAppointmentCountForDay(days[i]);
            if (appointmentsByDay[i] > maxAppointments) {
            maxAppointments = appointmentsByDay[i];
            maxAppointmentDay = days[i];
            }
        }


        // Build visualization string
        sb.setLength(0); // Clear StringBuilder
        for (int i = 0; i < days.length; i++) {
            sb.append(generateAppointmentBar(String.format("%-15s", days[i]), appointmentsByDay[i], maxAppointments));
        }
        
        System.out.println(StringUtils.beautify(sb.toString()));

        sb.setLength(0);

        sb.append(StringConstants.PEAK_HOUR_ANALYTICS_LOGO);
        sb.append(String.format("The day with the most appointments \nis \u001B[33m%s\u001B[0m with \u001B[32m%d\u001B[0m appointments.", maxAppointmentDay, maxAppointments));

        
        String mostCommonTime = AppointmentController.getMostCommonAppointmentTime();
        int mostCommonTimeCount = AppointmentController.getAppointmentCountForTime(mostCommonTime);
        int timePercentage = activeAppointments > 0 ? (mostCommonTimeCount * 100 / activeAppointments) : 0;
        
        sb.append(String.format("\nPeak Hour: %s (%d%% of appointments)\n\n", mostCommonTime, timePercentage));
        System.out.println(sb.toString());
        pause();

        // consume next line
        scanner.nextLine();
    }


    // 9. CHANGE PASSWORD
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

        // flush inpot buffer
        // print user relogin beautified
        System.out.println(StringUtils.beautify("Please log in again to continue."));
        scanner.nextLine();

        showLoginScreen();
        showMainMenu();
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
        System.out.println("\n\n" + StringUtils.beautify("Press Enter to continue..."));
        try {
            System.in.read();
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
    // use method overloading to simulate optional paramater
    private static void pause(boolean showMessage) {
        if (showMessage) {
            System.out.println("\n\n" + StringUtils.beautify("Press Enter to continue..."));
        }
        try {
            System.in.read();
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    private static String getValidInput(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            
            if (input.equals("0")) {
                return null;
            }
            
            if (input.trim().isEmpty()) {
                System.out.println("Error: " + fieldName + " cannot be empty. Please try again.");
                continue;
            }
            
            return input.trim();
        }
    }

    private static String getValidDoctorID(String prompt) {
        while (true) {
            String doctorID = getValidInput(prompt, "Doctor ID");
            
            if (doctorID == null) {
                return null;
            }
            
            // Assuming doctor IDs follow pattern "D" followed by numbers
            if (!doctorID.matches("D\\d+")) {
                System.out.println("Error: Invalid doctor ID format. Should start with 'D' followed by numbers.");
                continue;
            }
            
            return doctorID;
        }
    }

    private static String getValidNurseID(String prompt) {
        while (true) {
            String nurseID = getValidInput(prompt, "Nurse ID");
            
            if (nurseID == null) {
                return null;
            }
            
            // Assuming nurse IDs follow pattern "N" followed by numbers
            if (!nurseID.matches("N\\d+")) {
                System.out.println("Error: Invalid nurse ID format. Should start with 'N' followed by numbers.");
                continue;
            }
            
            return nurseID;
        }
    }

    // Helper method to generate demographic bar visualizations
    private static String generateDemographicBar(String label, int percentage) {
        StringBuilder bar = new StringBuilder(label + "  [");
        int barLength = 20;  // Total length of the bar
        int filledBlocks = (int)Math.round((percentage * barLength) / 100.0);
        
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filledBlocks ? "█" : "░");
        }
        
        bar.append(String.format("] %d%%\n", percentage));
        return bar.toString();
    }

    // Helper method to generate appointment trend bars  
    private static String generateAppointmentBar(String day, int count, int maxCount) {
        StringBuilder bar = new StringBuilder(day + " [");
        int barLength = 25;  // Total length of the bar
        int filledBlocks = (maxCount > 0) ? (count * barLength / maxCount) : 0;
        
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filledBlocks ? "█" : "░");
        }
        
        bar.append(String.format("] %d\n", count));
        return bar.toString();
    }
}