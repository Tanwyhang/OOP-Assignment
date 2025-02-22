package hospital.management.system.controllers;

import hospital.management.system.models.MedicalRecord;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordController {
    private List<MedicalRecord> records;

    public MedicalRecordController() {
        this.records = new ArrayList<>();
        loadRecordsFromFile();
    }

    public String createRecord(String patientID) {
        MedicalRecord record = new MedicalRecord("MR" + (records.size() + 1), LocalDateTime.now());
        records.add(record);
        saveRecordsToFile();
        return record.getRecordID();
    }

    public void addDiagnosis(String recordID, String diagnosis) {
        records.stream()
            .filter(record -> record.getRecordID().equals(recordID))
            .findFirst()
            .ifPresent(record -> {
                record.addDiagnosis(diagnosis);
                saveRecordsToFile();
            });
    }

    public void addPrescription(String recordID, String medication) {
        records.stream()
            .filter(record -> record.getRecordID().equals(recordID))
            .findFirst()
            .ifPresent(record -> {
                record.addPrescription(medication);
                saveRecordsToFile();
            });
    }

    public List<MedicalRecord> getFullHistory(String patientID) {
        return records; // Implement filtering by patientID if needed
    }

    public void saveRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/medical_records.csv"))) {
            for (MedicalRecord record : records) {
                writer.write(record.getRecordID() + "," + record.getDateCreated() + "," +
                        String.join(";", record.getDiagnoses()) + "," +
                        String.join(";", record.getPrescriptions()) + "," +
                        String.join(";", record.getTreatments()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving medical records to file: " + e.getMessage());
        }
    }

    public void loadRecordsFromFile() {
        File file = new File("data/medical_records.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    MedicalRecord record = new MedicalRecord(data[0], LocalDateTime.parse(data[1]));
                    for (String diagnosis : data[2].split(";")) {
                        record.addDiagnosis(diagnosis);
                    }
                    for (String prescription : data[3].split(";")) {
                        record.addPrescription(prescription);
                    }
                    for (String treatment : data[4].split(";")) {
                        record.addTreatment(treatment);
                    }
                    records.add(record);
                }
            } catch (IOException e) {
                System.err.println("Error loading medical records from file: " + e.getMessage());
            }
        }
    }
}