package models;

import utils.StringUtils;

public class Doctor extends Staff {
    private String specialization;
    public Doctor() {}

    public Doctor(String personID, String name, String address, String phoneNumber, char gender, int age,
                  String department, String shift, int yearsOfExperience, String specialization) {
        super(personID, name, address, phoneNumber, gender, age, department, shift, yearsOfExperience);
        this.specialization = specialization;
        
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    // to string similar to nurse
    @Override
    public String toString() {
        return StringUtils.beautify(String.format("""
                       ID             : %s
                       Name           : %s
                       Address        : %s
                       Phone          : %s
                       Gender         : %c
                       Age            : %d
                       Department     : %s
                       Shift          : %s
                       Experience     : %d years
                       Specialization : %s""",
            getPersonID(), getName(), getAddress(), getPhoneNumber(), getGender(), getAge(),
            getDepartment(), getShift(), getYearsOfExperience(), getSpecialization()
        ));
    }
}