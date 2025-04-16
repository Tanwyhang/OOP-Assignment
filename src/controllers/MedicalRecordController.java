package controllers;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import models.MedicalRecord;
import utils.StringUtils;

public final class MedicalRecordController implements ControllerInterface<MedicalRecord> {
    private final static List<MedicalRecord> records = new ArrayList<>();

    static  {
        loadRecordsFromFile();
    }

    @Override
    public void saveToFile() {
        saveRecordsToFile();
    }

    @Override
    public void loadFromFile() {
        loadRecordsFromFile();
    }

    @Override
    public List<MedicalRecord> getAll() {
        return new ArrayList<>(records);
    }

    @Override
    public String generateUniqueID() {
        return generateUniqueRecordID();
    }

    // generate unique record id, using while to prevent overlap
    private static String generateUniqueRecordID() {
        while (true) {
            int id = (int) (Math.random() * 10000); // Generates a number between 0 and 9999
            String candidate = "MR" + String.format("%04d", id);
            if (!records.stream().anyMatch(record -> record.getRecordID().equals(candidate))) {
                return candidate;
            }
        }
    }

    // create record using patient id
    public static String createRecord(String patientID) {
        String recordID = generateUniqueRecordID();
        MedicalRecord newRecord = new MedicalRecord(recordID, LocalDateTime.now());
        newRecord.setPatientID(patientID); // Set the patient ID

        records.add(newRecord);
        saveRecordsToFile();
        return recordID;
    }

    public static void addDiagnosis(String recordID, String diagnosis) {
        records.stream()
            .filter(record -> record.getRecordID().equals(recordID))
            .findFirst()
            .ifPresent(record -> {
                record.addDiagnosis(diagnosis);
                saveRecordsToFile();
            });
    }    

    public static void addPrescription(String recordID, String medication) {
        records.stream()
            .filter(record -> record.getRecordID().equals(recordID))
            .findFirst()
            .ifPresent(record -> {
                record.addPrescription(medication);
                saveRecordsToFile();
            });
    }

    // add treatment to record
    public static void addTreatment(String recordID, String treatment) {
        records.stream()
            .filter(record -> record.getRecordID().equals(recordID))
            .findFirst()
            .ifPresent(record -> {
                record.addTreatment(treatment);
                saveRecordsToFile();
            });
    }

    public static boolean removeRecord(String recordID){
        boolean removed = records.removeIf(record -> record.getRecordID().equals(recordID));
        if(removed){
            saveRecordsToFile();
        }
        return removed;
    }

    public List<MedicalRecord> getFullHistory(String patientID) {
        return records; // Implement filtering by patientID if needed
    }

    public static List<MedicalRecord> getRecordsByPatientID(String patientID) {
        List<MedicalRecord> filteredRecords = new ArrayList<>();
        for (MedicalRecord record : records) {
            if (patientID.equals(record.getPatientID())) {
                filteredRecords.add(record);
            }
        }
        return filteredRecords;
    }

    // display all records of a patient, make use of string builder, and stringutils beautify
    public static void displayRecords(String patientID) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Medical Records for Patient ").append(patientID).append(" ===\n");
        List<MedicalRecord> patientRecords = getRecordsByPatientID(patientID);
        if (patientRecords.isEmpty()) {
            sb.append("No records found for this patient.\n");
        } else {
            for (MedicalRecord record : patientRecords) {
                sb.append("Record ID: ").append(record.getRecordID()).append("\n")
                  .append("Date Created: ").append(record.getDateCreated()).append("\n")
                  .append("Diagnoses: ").append(String.join(", ", record.getDiagnoses())).append("\n")
                  .append("Prescriptions: ").append(String.join(", ", record.getPrescriptions())).append("\n")
                  .append("Treatments: ").append(String.join(", ", record.getTreatments())).append("\n\n");
            }
        }
        System.out.println(StringUtils.beautify(sb.toString()));
    }

    // return if the record exists
    public static boolean recordExists(String recordID) {
        return records.stream().anyMatch(record -> record.getRecordID().equals(recordID));
    }
    

    private static void saveRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/medical_records.csv"))) {
            for (MedicalRecord record : records) {
                writer.write(record.getRecordID() + "," + 
                             record.getPatientID() + "," + // Include patient ID
                             record.getDateCreated() + "," +
                             String.join(";", record.getDiagnoses()) + "->" +
                             String.join(";", record.getPrescriptions()) + "->" +
                             String.join(";", record.getTreatments()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving medical records to file: " + e.getMessage());
        }
    }

    private static void loadRecordsFromFile() {
        File file = new File("data/medical_records.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",", -1); // Use -1 to include trailing empty fields
                    if (data.length < 6) { // Ensure the record has at least 6 fields
                        System.err.println("Skipping invalid record: " + line);
                        continue;
                    }
                    try {
                        String recordID = data[0];
                        String patientID = data[1]; // Read patient ID
                        LocalDateTime dateTime = LocalDateTime.parse(data[2]);
                        String diagnoses = data[3];
                        String prescriptions = data[4];
                        String treatments = data[5];

                        MedicalRecord record = new MedicalRecord(recordID, dateTime);
                        record.setPatientID(patientID); // Set patient ID
                        if (!diagnoses.isEmpty()) record.addDiagnosis(diagnoses);
                        if (!prescriptions.isEmpty()) record.addPrescription(prescriptions);
                        if (!treatments.isEmpty()) record.addTreatment(treatments);
                        records.add(record);
                    } catch (Exception e) {
                        System.err.println("Error parsing record: " + line + " - " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading medical records from file: " + e.getMessage());
            }
        } else {
            System.out.println("Medical records file not found. Starting with an empty record list.");
        }
    }
}