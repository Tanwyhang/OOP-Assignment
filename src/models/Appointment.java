package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private String appointmentID;
    private LocalDateTime date;
    private String status;
    private String patientID;
    private String doctorID;
    private String roomID;

    public Appointment() {}

    public Appointment(String appointmentID, LocalDateTime date, String status) {
        this.appointmentID = appointmentID;
        this.date = date;
        this.status = status;
    }

    // Getters and Setters
    public String getAppointmentID() { return appointmentID; }
    public LocalDateTime getDate() { return date; }
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
    
    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }
    
    public String getDoctorID() { return doctorID; }
    public void setDoctorID(String doctorID) { this.doctorID = doctorID; }
    
    public String getRoomID() { return roomID; }
    public void setRoomID(String roomID) { this.roomID = roomID; }

    /**
     * Returns a human-readable string representation of the appointment
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Appointment ID: %s, Date: %s, Status: %s, Patient: %s, Doctor: %s, Room: %s",
                appointmentID,
                date.format(formatter),
                status,
                patientID,
                doctorID,
                roomID);
    }
}