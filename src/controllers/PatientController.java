package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import models.Patient;

public final class PatientController {
    private final List<Patient> patients;

    public PatientController() {
        this.patients = new ArrayList<>();
        loadPatientsFromFile();
    }

    public boolean registerPatient(String name, String address, String phoneNumber, char gender, int age) {
        String patientID = generateUniquePatientID();
        Patient newPatient = new Patient(patientID, name, address, phoneNumber, gender, age);
        
        // Add to list and save
        patients.add(newPatient);
        savePatientsToFile();
        return true;
    }
    
    private String generateUniquePatientID() {
        Random random = new Random();
        while (true) {
            int id = random.nextInt(10000); // Generates a number between(inclusive) 0 and 9999 
            String candidate = "P" + String.format("%04d", id);
            if (!patients.stream().anyMatch(p -> p.getPersonID().equals(candidate))) {
                return candidate;
            }
        }
    }

    public boolean dischargePatient(String patientID) {
        boolean removed = patients.removeIf(patient -> patient.getPersonID().equals(patientID));
        if (removed) {
            savePatientsToFile();
        }
        return removed;
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

    public Patient findPatientByID(String patientID) {
        return patients.stream()
            .filter(patient -> patient.getPersonID().equals(patientID))
            .findFirst()
            .orElse(null);
    }

    public void savePatientsToFile() {
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

    public void loadPatientsFromFile() {
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

    public List<Patient> getAllPatients() {
        return patients;
    }

    public List<Patient> searchPatientsByName(String name) {
        return patients.stream()
            .filter(patient -> patient.getName().toLowerCase().contains(name.toLowerCase()))
            .toList();
    }


}