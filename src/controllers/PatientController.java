package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import models.Patient;

public final class PatientController implements ControllerInterface<Patient> {
    private final static List<Patient> patients = new ArrayList<>();

    static {
        loadPatientsFromFile();
    }

    @Override
    public void saveToFile() {
        savePatientsToFile();
    }

    @Override
    public void loadFromFile() {
        loadPatientsFromFile();
    }

    @Override
    public List<Patient> getAll() {
        return getAllPatients();
    }
    
    @Override
    public String generateUniqueID() {
        return generateUniquePatientID();
    }

    public static boolean registerPatient(String name, String address, String phoneNumber, char gender, int age) {
        String patientID = generateUniquePatientID();
        Patient newPatient = new Patient(patientID, name, address, phoneNumber, gender, age);
        
        // Add to list and save
        patients.add(newPatient);
        savePatientsToFile();
        return true;
    }
    
    private static String generateUniquePatientID() {
        Random random = new Random();
        while (true) {
            int id = random.nextInt(10000); // Generates a number between(inclusive) 0 and 9999 
            String candidate = "P" + String.format("%04d", id);
            if (!patients.stream().anyMatch(p -> p.getPersonID().equals(candidate))) {
                return candidate;
            }
        }
    }

    /**
     * Displays all patients in a formatted 3-column grid with colored elements
     */
    public static void displayAllPatients() {
        patients.clear();
        loadPatientsFromFile();
        System.out.println("\n" + ColorCodes.BOLD + ColorCodes.BRIGHT_CYAN + "=== All Patients ===" + ColorCodes.RESET + "\n");
        
        if (patients.isEmpty()) {
            System.out.println(ColorCodes.ITALIC + ColorCodes.YELLOW + "No patients found." + ColorCodes.RESET);
            return;
        }
        
        // Define constants for formatting
        final int COLUMNS = 3;
        final int CELL_WIDTH = 30;
        final String HORIZONTAL_LINE = "+" + "-".repeat(CELL_WIDTH) + "+";
        
        // Calculate number of rows needed
        int totalPatients = patients.size();
        int rows = (totalPatients + COLUMNS - 1) / COLUMNS; // Ceiling division
        
        // Display patients in a grid
        for (int row = 0; row < rows; row++) {
            // Top border for each row
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(HORIZONTAL_LINE);
                if (col < COLUMNS - 1) System.out.print("  ");
            }
            System.out.println();
            
            // Patient info for this row
            for (int col = 0; col < COLUMNS; col++) {
                int index = row * COLUMNS + col;
                if (index < totalPatients) {
                    Patient patient = patients.get(index);
                    
                    // Format patient ID with color
                    String formattedID = ColorCodes.BOLD + ColorCodes.BRIGHT_YELLOW + 
                                        patient.getPersonID() + ColorCodes.RESET;
                    
                    // Format patient name
                    String name = ColorCodes.BRIGHT_WHITE + patient.getName() + ColorCodes.RESET;
                    
                    // Calculate padding to ensure consistent cell width
                    String cell = String.format("| %s - %s", formattedID, name);
                    // Need to account for ANSI codes when calculating visible length
                    String visibleText = patient.getPersonID() + " - " + patient.getName();
                    int padding = CELL_WIDTH - visibleText.length() - 1; // -1 for the initial |
                    
                    System.out.print(cell + " ".repeat(Math.max(0, padding)) + "|");
                } else {
                    // Empty cell if no more patients
                    System.out.print("|" + " ".repeat(CELL_WIDTH) + "|");
                }
                
                if (col < COLUMNS - 1) System.out.print("  ");
            }
            System.out.println();
            
            // Additional info row (age and gender)
            for (int col = 0; col < COLUMNS; col++) {
                int index = row * COLUMNS + col;
                if (index < totalPatients) {
                    Patient patient = patients.get(index);
                    
                    // Format age and gender info
                    String ageGender = ColorCodes.CYAN + "Age: " + 
                                      patient.getAge() + " | Gender: " + 
                                      patient.getGender() + ColorCodes.RESET;
                    
                    // Calculate padding
                    int visibleLength = ("Age: " + patient.getAge() + " | Gender: " + patient.getGender()).length();
                    int padding = CELL_WIDTH - visibleLength - 1; // -1 for the initial |
                    
                    System.out.print("| " + ageGender + " ".repeat(Math.max(0, padding)) + "|");
                } else {
                    // Empty cell
                    System.out.print("|" + " ".repeat(CELL_WIDTH) + "|");
                }
                
                if (col < COLUMNS - 1) System.out.print("  ");
            }
            System.out.println();
            
            // Bottom border for each row
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(HORIZONTAL_LINE);
                if (col < COLUMNS - 1) System.out.print("  ");
            }
            System.out.println("\n");
        }
        
        System.out.println("Total Patients: " + ColorCodes.BOLD + ColorCodes.GREEN + totalPatients + ColorCodes.RESET);
    }
    // Display patients using a parameter, a list of patients
    public static void displayPatients(List<Patient> patientList) {
        System.out.println("\n" + ColorCodes.BOLD + ColorCodes.BRIGHT_CYAN + "=== Display Patients ===" + ColorCodes.RESET + "\n");

        if (patientList.isEmpty()) {
            System.out.println(ColorCodes.ITALIC + ColorCodes.YELLOW + "No patients found." + ColorCodes.RESET);
            return;
        }

        final int COLUMNS = 3;
        final int CELL_WIDTH = 30;
        final String HORIZONTAL_LINE = "+" + "-".repeat(CELL_WIDTH) + "+";

        int totalPatients = patientList.size();
        int rows = (totalPatients + COLUMNS - 1) / COLUMNS;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(HORIZONTAL_LINE);
                if (col < COLUMNS - 1) System.out.print("  ");
            }
            System.out.println();

            for (int col = 0; col < COLUMNS; col++) {
                int index = row * COLUMNS + col;
                if (index < totalPatients) {
                    Patient patient = patientList.get(index);

                    String formattedID = ColorCodes.BOLD + ColorCodes.BRIGHT_YELLOW +
                            patient.getPersonID() + ColorCodes.RESET;

                    String name = ColorCodes.BRIGHT_WHITE + patient.getName() + ColorCodes.RESET;

                    String cell = String.format("| %s - %s", formattedID, name);
                    String visibleText = patient.getPersonID() + " - " + patient.getName();
                    int padding = CELL_WIDTH - visibleText.length() - 1;

                    System.out.print(cell + " ".repeat(Math.max(0, padding)) + "|");
                } else {
                    System.out.print("|" + " ".repeat(CELL_WIDTH) + "|");
                }

                if (col < COLUMNS - 1) System.out.print("  ");
            }
            System.out.println();

            for (int col = 0; col < COLUMNS; col++) {
                int index = row * COLUMNS + col;
                if (index < totalPatients) {
                    Patient patient = patientList.get(index);

                    String ageGender = ColorCodes.CYAN + "Age: " +
                            patient.getAge() + " | Gender: " +
                            patient.getGender() + ColorCodes.RESET;

                    int visibleLength = ("Age: " + patient.getAge() + " | Gender: " + patient.getGender()).length();
                    int padding = CELL_WIDTH - visibleLength - 1;

                    System.out.print("| " + ageGender + " ".repeat(Math.max(0, padding)) + "|");
                } else {
                    System.out.print("|" + " ".repeat(CELL_WIDTH) + "|");
                }

                if (col < COLUMNS - 1) System.out.print("  ");
            }
            System.out.println();

            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(HORIZONTAL_LINE);
                if (col < COLUMNS - 1) System.out.print("  ");
            }
            System.out.println("\n");
        }

        System.out.println("Total Patients: " + ColorCodes.BOLD + ColorCodes.GREEN + totalPatients + ColorCodes.RESET);
    }

    //View patient details
    public static void viewPatientDetails(String patientID) {
        Patient patient = findPatientByID(patientID);
        if (patient != null) {
            System.out.println("\n=== Patient Details ===\n");
            System.out.println(patient.toString());
        } else {
            System.out.println("Patient not found.");
        }
    }

    //Update patient details
    public static void updatePatientDetails(String patientID, String name, String address, String phoneNumber, char gender, int age) {
        Patient patient = findPatientByID(patientID);
        if (patient != null) {
            patient.setName(name);
            patient.setAddress(address);
            patient.setPhoneNumber(phoneNumber);
            patient.setGender(gender);
            patient.setAge(age);
            savePatientsToFile();
        } else {
            System.out.println("Patient not found.");
        }
    }

    public static boolean dischargePatient(String patientID) {
        boolean removed = patients.removeIf(patient -> patient.getPersonID().equals(patientID));
        if (removed) {
            savePatientsToFile();
        }
        return removed;
    }

    public static List<Patient> searchPatientsByAgeRange(int minAge, int maxAge) {
        List<Patient> filteredPatients = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getAge() >= minAge && patient.getAge() <= maxAge) {
                filteredPatients.add(patient);
            }
        }
        return filteredPatients;
    }

    // search patient by gender
    public static List<Patient> searchPatientsByGender(char gender) {
        List<Patient> filteredPatients = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getGender() == gender) {
                filteredPatients.add(patient);
            }
        }
        return filteredPatients;
    }

    // search by address keywords
    public static List<Patient> searchPatientsByAddress(String address) {
        List<Patient> filteredPatients = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getAddress().toLowerCase().contains(address.toLowerCase())) {
                filteredPatients.add(patient);
            }
        }
        return filteredPatients;
    }

    public static String getPatientName(String patientID) {
        return patients.stream()
            .filter(patient -> patient.getPersonID().equals(patientID))
            .map(Patient::getName)
            .findFirst()
            .orElse(null);
    }

    
    public void updatePatientDetails(String patientID, Map<String, String> details) {
        patients.stream()
            .filter(patient -> patient.getPersonID().equals(patientID))
            .findFirst()
            .ifPresent(patient -> {
                if (details.containsKey("name")) patient.setName(details.get("name"));
                if (details.containsKey("address")) patient.setAddress(details.get("address"));
                if (details.containsKey("phoneNumber")) patient.setPhoneNumber(details.get("phoneNumber"));
                savePatientsToFile();
            });
    }

    public static Patient findPatientByID(String patientID) {
        return patients.stream()
            .filter(patient -> patient.getPersonID().equals(patientID))
            .findFirst()
            .orElse(null);
    }

    private static void savePatientsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/patients.csv"))) {
            for (Patient patient : patients) {
                writer.write(patient.getPersonID() + "," + patient.getName() + "," + patient.getAddress() + "," +
                        patient.getPhoneNumber() + "," + patient.getGender() + "," + patient.getAge());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving patients to file: " + e.getMessage());
        }
    }

    private static void loadPatientsFromFile() {
        File file = new File("data/patients.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    patients.add(new Patient(data[0], data[1], data[2], data[3], data[4].charAt(0), Integer.parseInt(data[5])));
                }
            } catch (IOException e) {
                System.err.println("Error loading patients from file: " + e.getMessage());
            }
        }
    }

    private static List<Patient> getAllPatients() {
        return patients;
    }

    public static List<Patient> searchPatientsByName(String name) {
        return patients.stream()
            .filter(patient -> patient.getName().toLowerCase().contains(name.toLowerCase()))
            .toList();
    }

    public static boolean isPatientExist(String patientID) {
        // check if patient id exist in the patient array
        return patients.stream().anyMatch(patient -> patient.getPersonID().equals(patientID));
    }
}