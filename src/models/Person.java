package models;

public class Person {
    private final String personID;
    private String name;
    private String address;
    private String phoneNumber;
    private char gender;
    private int age;

    public Person() {
        this.personID = "";
        this.name = "";
        this.address = "";
        this.phoneNumber = "";
        this.gender = 'U'; // Assuming 'U' for unspecified
        this.age = 0;
    }

    public Person(String personID, String name, String address, String phoneNumber, char gender, int age) {
        this.personID = personID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        if (gender == 'M' || gender == 'F') {
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }
        this.age = age;
    }

    // Getters and Setters
    public String getPersonID() {
        return personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        if (gender == 'M' || gender == 'F') {
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}