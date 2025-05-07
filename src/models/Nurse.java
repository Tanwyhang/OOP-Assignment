package models;

import utils.StringUtils;

public class Nurse extends Staff {

    public Nurse() {}

    public Nurse(String personID, String name, String address, String phoneNumber, char gender, int age,
                 String department, String shift, int yearsOfExperience) {
        super(personID, name, address, phoneNumber, gender, age, department, shift, yearsOfExperience);

    }

    // to String method
    @Override
    public String toString() {
        return StringUtils.beautify(String.format("""
                       ID         : %s
                       Name       : %s
                       Address    : %s
                       Phone      : %s
                       Gender     : %c
                       Age        : %d
                       Department : %s
                       Shift      : %s
                       Experience : %d years""",
            getPersonID(), getName(), getAddress(), getPhoneNumber(), getGender(), getAge(),
            getDepartment(), getShift(), getYearsOfExperience()
        ));
    }
}