package org.tutortoise.service.student;

import jakarta.persistence.*;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.session.Session;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="student")
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Integer studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id_fk", nullable = false)
    private Parent parent;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="notes")
    private String notes;

    @OneToMany(mappedBy = "student")
    private List<Session> sessions = new ArrayList<>();

    public Student() {
    }

    public Student(Integer studentId, Parent parent, String firstName, String lastName, String notes, List<Session> sessions) {
        this.studentId = studentId;
        this.parent = parent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.notes = notes;
        this.sessions = sessions;
    }


}
