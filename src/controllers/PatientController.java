package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.Patient;

public final class PatientController {
    private final List<Patient> patients;

    public PatientController() {
        this.patients = new ArrayList<>();
        loadPatientsFromFile();
    }

    public boolean registerPatient(Patient patient) {
        patients.add(patient);
        savePatientsToFile();
        return true;
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