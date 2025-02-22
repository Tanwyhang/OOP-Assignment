package models;

public class Staff extends Person {
    private String department;
    private String shift;
    private int yearsOfExperience;

    public Staff() {}

    public Staff(String personID, String name, String address, String phoneNumber, char gender, int age,
                 String department, String shift, int yearsOfExperience) {
        super(personID, name, address, phoneNumber, gender, age);
        this.department = department;
        this.shift = shift;
        this.yearsOfExperience = yearsOfExperience;
    }

    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int years) { this.yearsOfExperience = years; }
}