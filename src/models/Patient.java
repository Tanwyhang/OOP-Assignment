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

    // Implement abstract methods
    @Override
    public String getPersonID() {
        return personID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public char getGender() {
        return gender;
    }

    @Override
    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
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