package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import models.Doctor;

public class DoctorController {
    private List<Doctor> doctors;

    public DoctorController() {
        this.doctors = new ArrayList<>();
        loadDoctorsFromFile();
    }

    public boolean hireDoctor(Doctor doctor) {
        doctors.add(doctor);
        saveDoctorsToFile();
        return true;
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

    public Doctor findDoctorByID(String doctorID) {
        return doctors.stream()
            .filter(doctor -> doctor.getPersonID().equals(doctorID))
            .findFirst()
            .orElse(null);
    }

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