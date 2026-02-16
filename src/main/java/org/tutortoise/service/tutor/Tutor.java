package org.tutortoise.service.tutor;

import jakarta.persistence.*;
import org.tutortoise.service.credit.CreditTransaction;
import org.tutortoise.service.session.Session;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tutor")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tutor_id")
    private Integer tutorId;

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


    @OneToMany(mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CreditTransaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();

    public Tutor() {
    }

    public Tutor(Integer tutorId, String firstName, String lastName, String email, String phone, String passwordEncrypted, List<CreditTransaction> transactions, List<Session> sessions) {
        this.tutorId = tutorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.passwordEncrypted = passwordEncrypted;
        this.transactions = transactions;
        this.sessions = sessions;
    }

    public Integer getTutorId() {
        return tutorId;
    }

    public void setTutorId(Integer tutorId) {
        this.tutorId = tutorId;
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

    public List<CreditTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<CreditTransaction> transactions) {
        this.transactions = transactions;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
