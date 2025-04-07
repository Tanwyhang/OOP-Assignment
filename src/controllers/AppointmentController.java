package controllers;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import models.Appointment;
import utils.DateTimeUtils;


public final class AppointmentController {
    private final static List<Appointment> appointments = new ArrayList<>();
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    static {
        loadAppointmentsFromFile();
    }

    // unique appointment ID generator Axxxx
    public static String generateUniqueAppointmentID() {
        Random random = new Random();
        boolean isUnique = false;
        String appointmentID = "";
        
        while (!isUnique) {
            // Generate a random 4-digit number
            int randomNum = 1000 + random.nextInt(9000); // Generates number between 1000-9999
            appointmentID = "A" + randomNum;
            
            // Check if this ID already exists
            final String finalID = appointmentID;
            isUnique = appointments.stream()
                    .noneMatch(appointment -> appointment.getAppointmentID().equals(finalID));
        }
        
        return appointmentID;
    }

    public String returnAppointmentID(){
        return "A" + (appointments.size());
    }

    public static boolean scheduleAppointment(String patientID, String doctorID, String roomID, LocalDateTime date) {
        String appointmentID = generateUniqueAppointmentID();
        Appointment appointment = new Appointment(appointmentID, date, "Scheduled");
        appointment.setPatientID(patientID);
        appointment.setDoctorID(doctorID);
        appointment.setRoomID(roomID);
        appointments.add(appointment);
        saveAppointmentsToFile();
        return true;
    }
    
    /**
     * Interactive method to schedule an appointment with guided user input
     * @param scanner Scanner for reading user input
     * @param patientID Patient's ID
     * @param roomController Instance of RoomController
     * @return true if appointment was scheduled successfully, false otherwise
     */
    public static boolean interactiveScheduleAppointment(Scanner scanner) {
        System.out.println("\n=== Schedule New Appointment ===");
        System.out.println("(Type '0' at any prompt to cancel the appointment scheduling process)\n\n");

        // Get patient ID
        String patientID;
        while (true) {
            PatientController.displayAllPatients();
            System.out.print("Enter patient ID (format: Pxxxx or 0 to cancel): ");
            patientID = scanner.nextLine().trim();
            if (patientID.equals("0")) {
                System.out.println("Appointment scheduling cancelled.");
                return false;
            }
            if (PatientController.isPatientExist(patientID)) break;
            System.out.println("Invalid patient ID. Please try again.");
        }

        // Get doctor ID
        String doctorID;
        while (true) {
            DoctorController.displayAllDoctors();
            System.out.print("Enter doctor ID (format: Dxxxx or 0 to cancel): ");
            doctorID = scanner.nextLine().trim();
            if (doctorID.equals("0")) {
                System.out.println("Appointment scheduling cancelled.");
                return false;
            }
            if (DoctorController.doctorExists(doctorID)) break;
            System.out.println("Invalid doctor ID. Please try again.");
        }

        // Get room ID
        String roomID;
        while (true) {
            RoomController.displayAllRooms();
            System.out.print("Enter room ID (format: Rxx or 0 to cancel): ");
            roomID = scanner.nextLine().trim();
            if (roomID.equals("0")) {
                System.out.println("Appointment scheduling cancelled.");
                return false;
            }
            if (RoomController.roomExists(roomID)) break;
            System.out.println("Invalid room ID. Please try again.");
        }

        // Get appointment date
        LocalDate selectedDate;
        while (true) {
            System.out.println("\n=== Available Dates ===");
            List<LocalDate> availableDates = generateAvailableDates();
            for (int i = 0; i < availableDates.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, DateTimeUtils.formatDate(availableDates.get(i)));
            }
            System.out.print("Enter the date number (or 0 to cancel): ");
            String dateInput = scanner.nextLine().trim();
            if (dateInput.equals("0")) {
                System.out.println("Appointment scheduling cancelled.");
                return false;
            }
            try {
                int dateIndex = Integer.parseInt(dateInput) - 1;
                if (dateIndex >= 0 && dateIndex < availableDates.size()) {
                    selectedDate = availableDates.get(dateIndex);
                    break;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid selection. Please try again.");
        }

        // Get appointment time
        LocalTime selectedTime;
        while (true) {
            System.out.println("\n=== Available Times for " + DateTimeUtils.formatDate(selectedDate) + " ===");
            List<LocalTime> availableTimes = generateAvailableTimes();
            for (int i = 0; i < availableTimes.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, availableTimes.get(i).format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            System.out.print("Enter the time number (or 0 to cancel): ");
            String timeInput = scanner.nextLine().trim();
            if (timeInput.equals("0")) {
                System.out.println("Appointment scheduling cancelled.");
                return false;
            }
            try {
                int timeIndex = Integer.parseInt(timeInput) - 1;
                if (timeIndex >= 0 && timeIndex < availableTimes.size()) {
                    selectedTime = availableTimes.get(timeIndex);
                    break;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid selection. Please try again.");
        }

        // Confirm appointment details
        LocalDateTime appointmentDateTime = DateTimeUtils.combineDateTime(selectedDate, selectedTime);
        System.out.println("\n=== Appointment Details ===");
        System.out.println("Patient ID: " + patientID);
        System.out.println("Doctor ID: " + doctorID);
        System.out.println("Room ID: " + roomID);
        System.out.println("Date & Time: " + DateTimeUtils.formatDateTime(appointmentDateTime));
        System.out.print("Confirm appointment (Y/N)? ");
        String confirmation = scanner.nextLine().trim();
        if (confirmation.equalsIgnoreCase("Y")) {
            boolean success = scheduleAppointment(patientID, doctorID, roomID, appointmentDateTime);
            if (success) {
                System.out.println("Appointment scheduled successfully!");
                return true;
            } else {
                System.out.println("Failed to schedule appointment.");
            }
        } else {
            System.out.println("Appointment scheduling cancelled.");
        }
        return false;
    }

    // Helper method to generate a list of available dates (next 7 days)
    private static List<LocalDate> generateAvailableDates() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            dates.add(today.plusDays(i));
        }
        return dates;
    }

    // Helper method to generate available time slots (9 AM to 5 PM, hourly)
    private static List<LocalTime> generateAvailableTimes() {
        List<LocalTime> times = new ArrayList<>();
        LocalTime start = LocalTime.of(9, 0); // 9:00 AM
        LocalTime end = LocalTime.of(17, 0);  // 5:00 PM
        
        while (!start.isAfter(end)) {
            times.add(start);
            start = start.plusHours(1);
        }
        return times;
    }

    public static boolean cancelAppointment(String appointmentID) {
        boolean removed = appointments.removeIf(appointment -> appointment.getAppointmentID().equals(appointmentID));
        if (removed) {
            saveAppointmentsToFile();
        }
        return removed;
    }

    public static boolean updateAppointmentStatus(String appointmentID, String status) {
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

    public static void displayAllAppointment(){
        System.out.printf("%-20s %-20s %-10s\n\n","AppointmentID","Date","Status");
        if(appointments.isEmpty()){
            System.out.println("No appointments scheduled");
        }else{
            for(Appointment appointment : appointments){
                System.out.printf("%-20s %-20s %-10s\n",
                    appointment.getAppointmentID(),
                    appointment.getDate().format(displayFormatter),
                    appointment.getStatus());
            }
        }
    }
    
    /**
     * Display appointments for a specific patient
     */
    public void displayPatientAppointments(String patientID) {
        System.out.printf("\n=== Appointments for Patient %s ===\n", patientID);
        System.out.printf("%-15s %-20s %-10s %-15s %-10s\n",
                "Appointment ID", "Date & Time", "Status", "Doctor", "Room");
        
        boolean found = false;
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID)) {
                System.out.printf("%-15s %-20s %-10s %-15s %-10s\n",
                    appointment.getAppointmentID(),
                    appointment.getDate().format(displayFormatter),
                    appointment.getStatus(),
                    appointment.getDoctorID(),
                    appointment.getRoomID());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No appointments found for this patient.");
        }
    }

    public static void saveAppointmentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/appointments.csv"))) {
            for (Appointment appointment : appointments) {
                writer.write(appointment.getAppointmentID() + "," + 
                             appointment.getDate() + "," + 
                             appointment.getStatus() + "," + 
                             appointment.getPatientID() + "," + 
                             appointment.getDoctorID() + "," + 
                             appointment.getRoomID());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving appointments to file: " + e.getMessage());
        }
    }

    private static void loadAppointmentsFromFile() {
        File file = new File("data/appointments.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 3) {
                        Appointment appointment = new Appointment(data[0], LocalDateTime.parse(data[1]), data[2]);
                        if (data.length >= 6) {
                            appointment.setPatientID(data[3]);
                            appointment.setDoctorID(data[4]);
                            appointment.setRoomID(data[5]);
                        }
                        appointments.add(appointment);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading appointments from file: " + e.getMessage());
            }
        }
    }
}