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
        String statusColored = status.equalsIgnoreCase("Active") ? "\u001B[32m" + status + "\u001B[0m" : 
                             status.equalsIgnoreCase("Scheduled") ? "\u001B[33m" + status + "\u001B[0m" : status;
        int colorCodeLength = 10; // Length of color codes (\u001B[32m and \u001B[0m)
        return String.format("%-15s %-20s %-" + (10 + colorCodeLength) + "s %-15s %-15s",
            appointmentID,
            date.format(formatter),
            statusColored,
            patientID == null ? "null" : patientID,
            doctorID == null ? "null" : doctorID);
    }
}