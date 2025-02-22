package hospital.management.system.models;

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
}