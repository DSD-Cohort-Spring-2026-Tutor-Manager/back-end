package org.tutortoise.service.parent;


import jakarta.persistence.*;
import lombok.Data;
import org.tutortoise.service.credit.CreditTransaction;
import org.tutortoise.service.session.Session;
import org.tutortoise.service.student.Student;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parent")
@Data
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password_encrypted")
    private String passwordEncrypted;

    @Column(name = "current_credit_amount")
    private double currentCreditAmount;

    @OneToMany(mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CreditTransaction> transactions = new ArrayList<>();

    public void addTransaction(CreditTransaction transaction) {
        transactions.add(transaction);
        transaction.setParent(this);
    }


    public Parent() {
    }

    public Parent(Integer parentId, String firstName, String lastName, String email, String phone, String passwordEncrypted, double currentCreditAmount, List<Session> sessions, List<Student> students, List<CreditTransaction> transactions) {
        this.parentId = parentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.passwordEncrypted = passwordEncrypted;
        this.currentCreditAmount = currentCreditAmount;
        this.sessions = sessions;
        this.students = students;
        this.transactions = transactions;
    }

}
