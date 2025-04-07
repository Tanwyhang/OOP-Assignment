package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String recordID;
    private String patientID;
    private LocalDateTime dateCreated;
    private List<String> diagnoses;
    private List<String> prescriptions;
    private List<String> treatments;

    public MedicalRecord() {
        this.diagnoses = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    public MedicalRecord(String recordID, LocalDateTime dateCreated) {
        this();
        this.recordID = recordID;
        this.dateCreated = dateCreated;
    }

    // Getters and Setters
    public String getRecordID() { return recordID; }
    public void addDiagnosis(String diagnosis) { diagnoses.add(diagnosis); }
    public void addPrescription(String prescription) { prescriptions.add(prescription); }
    public void addTreatment(String treatment) { treatments.add(treatment); }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public List<String> getDiagnoses() { return diagnoses; }
    public List<String> getPrescriptions() { return prescriptions; }
    public List<String> getTreatments() { return treatments; }

    // get patient id
    public String getPatientID() { return patientID; }
    // set patient id
    public void setPatientID(String patientID) { this.patientID = patientID; }

    public void updateDiagnosis(String oldDiagnosis, String newDiagnosis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}