package models;

import java.util.ArrayList;
import java.util.List;
import utils.StringUtils;

public class Patient extends Person {
    private final List<MedicalRecord> medicalHistory;

    public Patient(String personID, String name, String address, String phoneNumber, char gender, int age) {
        super(personID, name, address, phoneNumber, gender, age);
        this.medicalHistory = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Patient Details:\n");
        sb.append(String.format("%-15s: %s\n", "Person ID", personID));
        sb.append(String.format("%-15s: %s\n", "Name", name));
        sb.append(String.format("%-15s: %s\n", "Address", address));
        sb.append(String.format("%-15s: %s\n", "Phone Number", phoneNumber));
        sb.append(String.format("%-15s: %c\n", "Gender", gender));
        sb.append(String.format("%-15s: %d\n", "Age", age));
        
        String output = sb.toString();
        return StringUtils.beautify(output);
    }

    // Patient-specific methods
    public void addMedicalRecord(MedicalRecord record) {
        medicalHistory.add(record);
    }

    public List<MedicalRecord> getMedicalHistory() {
        return medicalHistory;
    }
}