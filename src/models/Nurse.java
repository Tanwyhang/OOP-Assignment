package models;

public class Nurse extends Staff {
    public Nurse() {}

    public Nurse(String personID, String name, String address, String phoneNumber, char gender, int age,
                 String department, String shift, int yearsOfExperience) {
        super(personID, name, address, phoneNumber, gender, age, department, shift, yearsOfExperience);
    }

    public void changeDepartment(String department) {
        setDepartment(department);
    }
}