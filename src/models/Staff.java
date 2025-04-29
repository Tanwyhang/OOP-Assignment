package models;

import utils.StringUtils;

public class Staff extends Person {
    protected String department;
    protected String shift;
    protected int yearsOfExperience;

    public Staff() {}

    public Staff(String personID, String name, String address, String phoneNumber, char gender, int age,
                 String department, String shift, int yearsOfExperience) {
        super(personID, name, address, phoneNumber, gender, age);
        this.department = department;
        this.shift = shift;
        this.yearsOfExperience = yearsOfExperience;
    }

    // to String method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Staff Details:\n");
        // have even spacing for :
        sb.append(String.format("%-15s: %s\n", "Person ID", personID));
        sb.append(String.format("%-15s: %s\n", "Name", name));
        sb.append(String.format("%-15s: %s\n", "Address", address));
        sb.append(String.format("%-15s: %s\n", "Phone Number", phoneNumber));
        sb.append(String.format("%-15s: %c\n", "Gender", gender));
        sb.append(String.format("%-15s: %d\n", "Age", age));
        sb.append(String.format("%-15s: %s\n", "Department", department));
        sb.append(String.format("%-15s: %s\n", "Shift", shift));
        sb.append(String.format("%-15s: %d\n", "Experience", yearsOfExperience));

        String output = sb.toString();
        return StringUtils.beautify(output);
    }

    // Getters and Setters for Staff-specific fields
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int years) { this.yearsOfExperience = years; }
}