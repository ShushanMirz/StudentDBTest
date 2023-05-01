package org.example;

public class StudentUpdate {

    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private boolean isWarVeteran;
    private String birthdate;


    public StudentUpdate(String firstName, String lastName, String middleName, String email, String phone, boolean isWarVeteran, String birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.phone = phone;
        this.isWarVeteran = isWarVeteran;
        this.birthdate = birthdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isWarVeteran() {
        return isWarVeteran;
    }

    public void setWarVeteran(boolean warVeteran) {
        isWarVeteran = warVeteran;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "StudentUpdate{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", isWarVeteran=" + isWarVeteran +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }
}
