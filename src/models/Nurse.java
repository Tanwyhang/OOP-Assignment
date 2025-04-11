package models;

public class Nurse extends Person {
    private String department;
    private String shift;
    private int yearsOfExperience;

    public Nurse() {}

    public Nurse(String personID, String name, String address, String phoneNumber, char gender, int age,
                 String department, String shift, int yearsOfExperience) {
        super(personID, name, address, phoneNumber, gender, age);
        this.department = department;
        this.shift = shift;
        this.yearsOfExperience = yearsOfExperience;
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

    // to String method
    @Override
    public String toString() {
        return String.format("""
                             ===Nurse Details===
                               ID         : %s
                               Name       : %s
                               Address    : %s
                               Phone      : %s
                               Gender     : %c
                               Age        : %d
                               Department : %s
                               Shift      : %s
                               Experience : %d years""",
            personID, name, address, phoneNumber, gender, age,
            department, shift, yearsOfExperience
        );
    }

    // Nurse-specific methods
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}