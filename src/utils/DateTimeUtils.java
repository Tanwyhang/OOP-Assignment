package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateTimeUtils {
    
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
    /**
     * Interactive method to get a valid date from user input
     * @param scanner Scanner for user input
     * @return LocalDate object representing user's selected date
     */
    public static LocalDate getValidDateFromUser(Scanner scanner) {
        LocalDate selectedDate = null;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.println("\nEnter appointment date (dd/MM/yyyy) or 'today' for today's date: ");
            String dateInput = scanner.nextLine().trim();
            
            try {
                if (dateInput.equalsIgnoreCase("today")) {
                    selectedDate = LocalDate.now();
                    validInput = true;
                } else {
                    selectedDate = LocalDate.parse(dateInput, dateFormatter);
                    
                    // Check if the date is in the past
                    if (selectedDate.isBefore(LocalDate.now())) {
                        System.out.println("Error: Cannot schedule appointments in the past.");
                        continue;
                    }
                    
                    validInput = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy format.");
            }
        }
        
        return selectedDate;
    }
    
    /**
     * Interactive method to get a valid time from user input
     * @param scanner Scanner for user input
     * @param selectedDate The previously selected date
     * @return LocalTime object representing user's selected time
     */
    public static LocalTime getValidTimeFromUser(Scanner scanner, LocalDate selectedDate) {
        LocalTime selectedTime = null;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.println("\nEnter appointment time (HH:mm, 24-hour format): ");
            String timeInput = scanner.nextLine().trim();
            
            try {
                selectedTime = LocalTime.parse(timeInput, timeFormatter);
                
                // Check if the time is valid (not in the past)
                LocalDateTime appointmentDateTime = LocalDateTime.of(selectedDate, selectedTime);
                if (selectedDate.isEqual(LocalDate.now()) && appointmentDateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Error: Cannot schedule appointments in the past.");
                    continue;
                }
                
                validInput = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm (24-hour) format.");
            }
        }
        
        return selectedTime;
    }
    
    /**
     * Combines date and time into a LocalDateTime object
     */
    public static LocalDateTime combineDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }
    
    /**
     * Format LocalDate to a user-friendly string
     */
    public static String formatDate(LocalDate date) {
        return date.format(dateFormatter);
    }
    
    /**
     * Format LocalDateTime to a user-friendly string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }
}
