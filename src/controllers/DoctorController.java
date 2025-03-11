package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.Doctor;
import models.Patient;

public final class DoctorController {
    private final List<Doctor> doctors;

    public DoctorController() {
        this.doctors = new ArrayList<>();
        loadDoctorsFromFile();
    }

    public boolean hireDoctor(String name, String address, String phoneNumber, char gender, int age, String department, String shift, int yearsOfExperience, String specialization) {
        String personID = generateUniquePersonID();
        Doctor newDoctor = new Doctor(personID, name, address, phoneNumber, gender, age, department, shift, yearsOfExperience, specialization);

        doctors.add(newDoctor);
        saveDoctorsToFile();
        return true;
    }

    // View doctor details
    public void viewDoctorDetails(String personID) {
        Doctor doctor = findDoctorByID(personID);
        if (doctor != null) {
            // Show doctor details
            System.out.println("\n=== Doctor Details ===\n");
            System.out.println("Doctor ID          : " + doctor.getPersonID());
            System.out.println("Name               : " + doctor.getName());
            System.out.println("Address            : " + doctor.getAddress());
            System.out.println("Phone Number       : " + doctor.getPhoneNumber());
            System.out.println("Gender             : " + doctor.getGender());
            System.out.println("Age                : " + doctor.getAge());
            System.out.println("Department         : " + doctor.getDepartment());
            System.out.println("Shift              : " + doctor.getShift());
            System.out.println("Years of Experience: " + doctor.getYearsOfExperience());
            System.out.println("Specialization     : " + doctor.getSpecialization());
        } else {
            System.out.println("Doctor not found.");
        }
    }

    private String generateUniquePersonID() {
        Random random = new Random();
        while (true) {
            int id = random.nextInt(10000); // Generates a number between(inclusive) 0 and 9999 
            String candidate = "D" + String.format("%04d", id);
            if (!doctors.stream().anyMatch(p -> p.getPersonID().equals(candidate))) {
                return candidate;
            }
        }
    }

    public boolean removeDoctor(String doctorID) {
        boolean removed = doctors.removeIf(doctor -> doctor.getPersonID().equals(doctorID));
        if (removed) {
            saveDoctorsToFile();
        }
        return removed;
    }

    public void updateSpecialization(String doctorID, String specialization) {
        doctors.stream()
            .filter(doctor -> doctor.getPersonID().equals(doctorID))
            .findFirst()
            .ifPresent(doctor -> {
                doctor.setSpecialization(specialization);
                saveDoctorsToFile();
            });
    }

    public void displayAllDoctors() {
        System.out.println("\n=== All Doctors ===\n");
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            // Display DoctorID and Name
            doctors.forEach(doctor -> System.out.println(doctor.getPersonID() + " - " + doctor.getName()));
        }
    }

    public Doctor findDoctorByID(String doctorID) {
        return doctors.stream()
            .filter(doctor -> doctor.getPersonID().equals(doctorID))
            .findFirst()
            .orElse(null);
    }

    public List<Doctor> searchDoctorsByName(String doctorName) {
        return doctors.stream()
            .filter(d -> d.getName().equalsIgnoreCase(doctorName))
            .toList();
    }

    public List<Doctor> getDoctorsByDepartment(String department) {
        return doctors.stream()
            .filter(d -> d.getDepartment().equalsIgnoreCase(department))
            .toList();
    }

    public List<Doctor> getDoctorsByExperience(int yearsOfExperience) {
        return doctors.stream()
            .filter(d -> d.getYearsOfExperience() >= yearsOfExperience)
            .toList();
    }

    // FILE IO HANDLING
    public void saveDoctorsToFile() {
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

    public void loadDoctorsFromFile() {
        File file = new File("data/doctors.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    doctors.add(new Doctor(data[0], data[1], data[2], data[3], data[4].charAt(0), Integer.parseInt(data[5]),
                            data[6], data[7], Integer.parseInt(data[8]), data[9]));
                }
            } catch (IOException e) {
                System.err.println("Error loading doctors from file: " + e.getMessage());
            }
        }
    }
}