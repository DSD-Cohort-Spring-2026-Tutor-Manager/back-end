package org.tutorial.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Parent")
public class Parent {

    @Id
    @Column(name="parent_id")
    private int parentId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="password_encrypted")
    private String passwordEncrypted;

    @Column(name="current_credit_amount")
    private double currentCreditAmount;

    public Parent() {
    }

    public Parent(int parentId, String lastName, String firstName, String email, String phone, String passwordEncrypted, double currentCreditAmount) {
        this.parentId = parentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.passwordEncrypted = passwordEncrypted;
        this.currentCreditAmount = currentCreditAmount;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public String getPasswordEncrypted() {
        return passwordEncrypted;
    }

    public void setPasswordEncrypted(String passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
    }

    public double getCurrentCreditAmount() {
        return currentCreditAmount;
    }

    public void setCurrentCreditAmount(double currentCreditAmount) {
        this.currentCreditAmount = currentCreditAmount;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "parentId=" + parentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", passwordEncrypted='" + passwordEncrypted + '\'' +
                ", currentCreditAmount=" + currentCreditAmount +
                '}';
    }
}
