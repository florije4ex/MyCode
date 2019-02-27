package com.cui.code.test;

import java.util.List;

/**
 * @author cuishixiang
 * @date 2019-02-27
 */
public class Customer {
    private String firstName;
    private String lastName;
    private int age;
    private List<Contact> contactDetails;
    private Address homeAddress;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Contact> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(List<Contact> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", contactDetails=" + contactDetails +
                ", homeAddress=" + homeAddress +
                '}';
    }
}
