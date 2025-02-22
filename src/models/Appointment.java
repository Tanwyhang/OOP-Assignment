package models;

import java.time.LocalDateTime;

public class Appointment {
    private String appointmentID;
    private LocalDateTime date;
    private String status;

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
}