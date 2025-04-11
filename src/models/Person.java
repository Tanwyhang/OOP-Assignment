package models;

abstract class Person {
    protected final String personID;
    protected String name;
    protected String address;
    protected String phoneNumber;
    protected char gender;
    protected int age;

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

    // Abstract Getters and Setters
    public abstract String getPersonID();
    public abstract String getName();
    public abstract void setName(String name);
    public abstract String getAddress();
    public abstract void setAddress(String address);
    public abstract String getPhoneNumber();
    public abstract void setPhoneNumber(String phoneNumber);
    public abstract char getGender();
    public abstract void setGender(char gender);
    public abstract int getAge();
    public abstract void setAge(int age);
    
    // abstract toString method
    @Override
    public abstract String toString();
}