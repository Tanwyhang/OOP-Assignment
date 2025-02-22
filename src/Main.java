package hospital.management.system;

import hospital.management.system.controllers.*;
import hospital.management.system.models.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RoomController roomController = new RoomController();
        PatientController patientController = new PatientController();
        DoctorController doctorController = new DoctorController();
        AppointmentController appointmentController = new AppointmentController();
        MedicalRecordController medicalRecordController = new MedicalRecordController();

        while (true) {
            System.out.println("\nHospital Management System");
            System.out.println("1. Add Room");
            System.out.println("2. Register Patient");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room type: ");
                    String type = scanner.nextLine();
                    roomController.addRoom(type);
                    System.out.println("Room added successfully!");
                    break;
                case 2:
                    System.out.print("Enter patient ID: ");
                    String personID = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter gender (M/F): ");
                    char gender = scanner.nextLine().charAt(0);
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Patient patient = new Patient(personID, name, address, phoneNumber, gender, age);
                    patientController.registerPatient(patient);
                    System.out.println("Patient registered successfully!");
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}