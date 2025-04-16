package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.Nurse;
import utils.StringUtils;

public final class NurseController implements ControllerInterface<Nurse> {
    private final static List<Nurse> nurses = new ArrayList<>();

    static {
        loadNursesFromFile();
    }

    @Override
    public void saveToFile() {
        saveNursesToFile();
    }

    @Override
    public void loadFromFile() {
        loadNursesFromFile();
    }

    @Override
    public List<Nurse> getAll() {
        return new ArrayList<>(nurses);
    }
    
    @Override
    public String generateUniqueID() {
        return generateUniquePersonID();
    }

    public static boolean hireNurse(String name, String address, String phoneNumber, char gender, int age, String department, String shift, int yearsOfExperience) {
        String personID = generateUniquePersonID();
        Nurse newNurse = new Nurse(personID, name, address, phoneNumber, gender, age, department, shift, yearsOfExperience);

        nurses.add(newNurse);
        saveNursesToFile();
        return true;
    }

    public static void viewNurseDetails(String personID) {
        Nurse nurse = findNurseByID(personID);
        if (nurse != null) {
            System.out.println("\n=== Nurse Details ===\n");
            System.out.println(nurse.toString());
        } else {
            System.out.println("Nurse not found.");
        }
    }

    public static Nurse findNurseByID(String nurseID) {
        return nurses.stream()
            .filter(nurse -> nurse.getPersonID().equals(nurseID))
            .findFirst()
            .orElse(null);
    }

    public static List<Nurse> searchNursesByName(String nurseName) {
        return nurses.stream()
            .filter(n -> n.getName().contains(nurseName))
            .toList();
    }

    public static List<Nurse> getNursesByDepartment(String department) {
        return nurses.stream()
            .filter(n -> n.getDepartment().equalsIgnoreCase(department))
            .toList();
    }

    public static List<Nurse> getNursesByExperience(int yearsOfExperience) {
        return nurses.stream()
            .filter(n -> n.getYearsOfExperience() >= yearsOfExperience)
            .toList();
    }

    public static List<Nurse> searchNursesByAgeRange(int minAge, int maxAge) {
        return nurses.stream()
            .filter(nurse -> nurse.getAge() >= minAge && nurse.getAge() <= maxAge)
            .toList();
    }

    public static List<Nurse> searchNursesByGender(char gender) {
        return nurses.stream()
            .filter(nurse -> nurse.getGender() == gender)
            .toList();
    }

    public static List<Nurse> getNursesByShift(String shift) {
        return nurses.stream()
            .filter(nurse -> nurse.getShift().equalsIgnoreCase(shift))
            .toList();
    }

    public static void updateDepartment(String nurseID, String department) {
        nurses.stream()
            .filter(nurse -> nurse.getPersonID().equals(nurseID))
            .findFirst()
            .ifPresent(nurse -> {
                nurse.setDepartment(department);
                saveNursesToFile();
            });
    }

    public static void updateShift(String nurseID, String shift) {
        nurses.stream()
            .filter(nurse -> nurse.getPersonID().equals(nurseID))
            .findFirst()
            .ifPresent(nurse -> {
                nurse.setShift(shift);
                saveNursesToFile();
            });
    }

    public static void updateExperience(String nurseID, int yearsOfExperience) {
        nurses.stream()
            .filter(nurse -> nurse.getPersonID().equals(nurseID))
            .findFirst()
            .ifPresent(nurse -> {
                nurse.setYearsOfExperience(yearsOfExperience);
                saveNursesToFile();
            });
    }

    private static String generateUniquePersonID() {
        Random random = new Random();
        while (true) {
            int id = random.nextInt(10000);
            String candidate = "N" + String.format("%04d", id);
            if (!nurses.stream().anyMatch(n -> n.getPersonID().equals(candidate))) {
                return candidate;
            }
        }
    }

    public static boolean removeNurse(String nurseID) {
        boolean removed = nurses.removeIf(nurse -> nurse.getPersonID().equals(nurseID));
        if (removed) {
            saveNursesToFile();
        }
        return removed;
    }

    public static void displayAllNurses() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== All Nurses ===\n\n");
        
        if (nurses.isEmpty()) {
            sb.append("No nurses found.");
        } else {
            nurses.forEach(nurse -> {
                String formattedLine = String.format("%-8s - %s", 
                    nurse.getPersonID(), 
                    nurse.getName());
                sb.append(formattedLine).append("\n");
            });
        }
        
        System.out.print(StringUtils.beautify(sb.toString()));
    }

    public static boolean nurseExists(String nurseID) {
        return nurses.stream().anyMatch(nurse -> nurse.getPersonID().equals(nurseID));
    }

    public static void saveNursesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/nurses.csv"))) {
            for (Nurse nurse : nurses) {
                writer.write(nurse.getPersonID() + "," + nurse.getName() + "," + nurse.getAddress() + "," +
                        nurse.getPhoneNumber() + "," + nurse.getGender() + "," + nurse.getAge() + "," +
                        nurse.getDepartment() + "," + nurse.getShift() + "," + nurse.getYearsOfExperience());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving nurses to file: " + e.getMessage());
        }
    }

    private static void loadNursesFromFile() {
        File file = new File("data/nurses.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    nurses.add(new Nurse(data[0], data[1], data[2], data[3], data[4].charAt(0), Integer.parseInt(data[5]),
                            data[6], data[7], Integer.parseInt(data[8])));
                }
            } catch (IOException e) {
                System.err.println("Error loading nurses from file: " + e.getMessage());
            }
        }
    }
}
