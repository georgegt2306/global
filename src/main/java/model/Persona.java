package model;

import java.io.Serializable;

public class Persona implements Serializable {

    private Integer id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String address;

    private String email;

    private String birthDate;

    private String phone;

    private String estate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    @Override
    public String toString() {
        return "Persona [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age="
                + age + ", address=" + address + ", email=" + email + ", birthDate=" + birthDate + ", phone=" + phone
                + ", estate=" + estate + "]";
    }

}
