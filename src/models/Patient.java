package models;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private final List<MedicalRecord> medicalHistory;


    public Patient(String personID, String name, String address, String phoneNumber, char gender, int age) {
        super(personID, name, address, phoneNumber, gender, age);
        this.medicalHistory = new ArrayList<>();
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalHistory.add(record);
    }

    public List<MedicalRecord> getMedicalHistory() {
        return medicalHistory;
    }

    @Override
    String show_profile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}