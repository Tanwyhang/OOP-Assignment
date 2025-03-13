package controllers;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import models.Appointment;

public final class AppointmentController {
    private final List<Appointment> appointments;

    public AppointmentController() {
        this.appointments = new ArrayList<>();
        loadAppointmentsFromFile();
    }

    public String returnAppointmentID(){
        return "A" + (appointments.size());
    }

    public boolean scheduleAppointment(String patientID, String doctorID, String roomID, LocalDateTime date) {
        Appointment appointment = new Appointment("A" + (appointments.size() + 1), date, "Scheduled");
        appointments.add(appointment);
        returnAppointmentID();
        saveAppointmentsToFile();
        return true;
    }

    public boolean cancelAppointment(String appointmentID) {
        boolean removed = appointments.removeIf(appointment -> appointment.getAppointmentID().equals(appointmentID));
        if (removed) {
            saveAppointmentsToFile();
        }
        return removed;
    }

    public boolean updateAppointmentStatus(String appointmentID, String status) {
        return appointments.stream()
            .filter(appointment -> appointment.getAppointmentID().equals(appointmentID))
            .findFirst()
            .map(appointment -> {
                appointment.setStatus(status);
                saveAppointmentsToFile();
                return true;
            })
            .orElse(false);
    }

    public void displayAllAppointment(){
        System.out.printf("%-20s %-20s %-10s\n\n","AppointmentID","Date","Status");
        if(appointments.isEmpty()){
            System.out.println("No appointments scheduled");
        }else{
            for(Appointment appointment : appointments){
                System.out.printf("%-20s %-20s %-10s\n",appointment.getAppointmentID(),appointment.getDate(),appointment.getStatus());
            }
        }
        }
        

    public void saveAppointmentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/appointments.csv"))) {
            for (Appointment appointment : appointments) {
                writer.write(appointment.getAppointmentID() + "," + appointment.getDate() + "," + appointment.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving appointments to file: " + e.getMessage());
        }
    }

    public void loadAppointmentsFromFile() {
        File file = new File("data/appointments.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    appointments.add(new Appointment(data[0], LocalDateTime.parse(data[1]), data[2]));
                }
            } catch (IOException e) {
                System.err.println("Error loading appointments from file: " + e.getMessage());
            }
        }
    }
}