package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.Doctor;

public final class DoctorController implements ControllerInterface<Doctor> {
    private final static List<Doctor> doctors = new ArrayList<>();
    
    static  {
        loadDoctorsFromFile();
    }

    @Override
    public void saveToFile() {
        saveDoctorsToFile();
    }

    @Override
    public void loadFromFile() {
        loadDoctorsFromFile();
    }

    @Override
    public List<Doctor> getAll() {
        return new ArrayList<>(doctors);
    }

    @Override
    public String generateUniqueID() {
        return generateUniquePersonID();
    }

    public static boolean hireDoctor(String name, String address, String phoneNumber, char gender, int age, String department, String shift, int yearsOfExperience, String specialization) {
        String personID = generateUniquePersonID();
        Doctor newDoctor = new Doctor(personID, name, address, phoneNumber, gender, age, department, shift, yearsOfExperience, specialization);

        doctors.add(newDoctor);
        saveDoctorsToFile();
        return true;
    }

    // View doctor details
    public static void viewDoctorDetails(String personID) {
        Doctor doctor = findDoctorByID(personID);
        if (doctor != null) {
            System.out.println("\n=== Doctor Details ===\n");
            System.out.println(doctor.toString());
        } else {
            System.out.println("Doctor not found.");
        }
    }

    // SEARCH METHODS
    
    public static Doctor findDoctorByID(String doctorID) {
        return doctors.stream()
            .filter(doctor -> doctor.getPersonID().equals(doctorID))
            .findFirst()
            .orElse(null);
    }

    public static List<Doctor> searchDoctorsByName(String doctorName) {
        return doctors.stream()
            .filter(d -> d.getName().toLowerCase().contains(doctorName.toLowerCase()))
            .toList();
    }

    public static List<Doctor> getDoctorsByDepartment(String department) {
        return doctors.stream()
            .filter(d -> d.getDepartment().equalsIgnoreCase(department))
            .toList();
    }

    public static List<Doctor> getDoctorsByExperience(int yearsOfExperience) {
        return doctors.stream()
            .filter(d -> d.getYearsOfExperience() >= yearsOfExperience)
            .toList();
    }

    public static List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctors.stream()
            .filter(doctor -> doctor.getSpecialization().contains(specialization))
            .toList();
    }

    public static List<Doctor> searchDoctorsByAgeRange(int minAge, int maxAge) {
        return doctors.stream()
            .filter(doctor -> doctor.getAge() >= minAge && doctor.getAge() <= maxAge)
            .toList();
    }

    public static List<Doctor> searchDoctorsByGender(char gender) {
        return doctors.stream()
            .filter(doctor -> doctor.getGender() == gender)
            .toList();
    }

    public static List<Doctor> getDoctorsByShift(String shift) {
        return doctors.stream()
            .filter(doctor -> doctor.getShift().equalsIgnoreCase(shift))
            .toList();
    }

    // UPDATE METHODS
    public static void updateSpecialization(String doctorID, String specialization) {
        doctors.stream()
            .filter(doctor -> doctor.getPersonID().equals(doctorID))
            .findFirst()
            .ifPresent(doctor -> {
                doctor.setSpecialization(specialization);
                saveDoctorsToFile();
            });
    }

    public static void updateDepartment(String doctorID, String department) {
        doctors.stream()
            .filter(doctor -> doctor.getPersonID().equals(doctorID))
            .findFirst()
            .ifPresent(doctor -> {
                doctor.setDepartment(department);
                saveDoctorsToFile();
            });
    }

    public static void updateShift(String doctorID, String shift) {
        doctors.stream()
            .filter(doctor -> doctor.getPersonID().equals(doctorID))
            .findFirst()
            .ifPresent(doctor -> {
                doctor.setShift(shift);
                saveDoctorsToFile();
            });
    }

    public static void updateExperience(String doctorID, int yearsOfExperience) {
        doctors.stream()
            .filter(doctor -> doctor.getPersonID().equals(doctorID))
            .findFirst()
            .ifPresent(doctor -> {
                doctor.setYearsOfExperience(yearsOfExperience);
                saveDoctorsToFile();
            });
    }

    // OTHER METHODS
    private static String generateUniquePersonID() {
        Random random = new Random();
        while (true) {
            int id = random.nextInt(10000); // Generates a number between(inclusive) 0 and 9999 
            String candidate = "D" + String.format("%04d", id);
            if (!doctors.stream().anyMatch(p -> p.getPersonID().equals(candidate))) {
                return candidate;
            }
        }
    }

    public static boolean removeDoctor(String doctorID) {
        boolean removed = doctors.removeIf(doctor -> doctor.getPersonID().equals(doctorID));
        if (removed) {
            saveDoctorsToFile();
        }
        return removed;
    }

    public static void displayAllDoctors() {
        System.out.println("\n=== All Doctors ===\n");
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            // Display DoctorID and Name
            doctors.forEach(doctor -> System.out.println(doctor.getPersonID() + " - " + doctor.getName()));
        }
    }

    // Method to check whether the doctor exists
    public static boolean doctorExists(String doctorID) {
        return doctors.stream().anyMatch(doctor -> doctor.getPersonID().equals(doctorID));
    }

    // FILE IO HANDLING
    private static void saveDoctorsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/doctors.csv"))) {
            for (Doctor doctor : doctors) {
                writer.write(doctor.getPersonID() + "," + doctor.getName() + "," + doctor.getAddress() + "," +
                        doctor.getPhoneNumber() + "," + doctor.getGender() + "," + doctor.getAge() + "," +
                        doctor.getDepartment() + "," + doctor.getShift() + "," + doctor.getYearsOfExperience() + "," +
                        doctor.getSpecialization());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving doctors to file: " + e.getMessage());
        }
    }

    private static void loadDoctorsFromFile() {
        File file = new File("data/doctors.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    // Ensure the data array has the expected number of elements
                    if (data.length == 10) {
                        doctors.add(new Doctor(data[0], data[1], data[2], data[3], data[4].charAt(0), Integer.parseInt(data[5]),
                                data[6], data[7], Integer.parseInt(data[8]), data[9]));
                    } else {
                        System.err.println("Invalid data format in line: " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading doctors from file: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error parsing number in file: " + e.getMessage());
            }
        }
    }
}