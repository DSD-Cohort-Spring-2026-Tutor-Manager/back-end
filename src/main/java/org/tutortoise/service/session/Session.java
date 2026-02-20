package org.tutortoise.service.session;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.tutortoise.service.credit.CreditTransaction;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.student.Student;
import org.tutortoise.service.tutor.Tutor;

@Entity
@Table(name="session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="parent_id_fk", nullable = false)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="student_id_fk", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tutor_id_fk", nullable = false)
    private Tutor tutor;

    @Column(name="duration_hours")
    private Double durationsHours;

    @Enumerated(EnumType.STRING)
    @Column(name="session_status")
    private SessionStatus sessionStatus;

    @Column(name="datetime_started" )
    private LocalDateTime datetimeStarted;

    @Column(name = "assessment_points_earned")
    private Double assessmentPointsEarned;

    @Column(name = "assessment_points_goal")
    private Double assessmentPointsGoal;

    @Column(name = "assessment_points_max")
    private Double assessmentPointsMax;

    @OneToMany(mappedBy = "session")
    private List<CreditTransaction> transactions = new ArrayList<>();


    public Session() {
    }

    public Session(Integer sessionId, Parent parent, Student student, Tutor tutor, Double durationsHours, LocalDateTime datetimeStarted, SessionStatus sessionStatus, Double assessmentPointsEarned, Double assessmentPointsGoal, Double assessmentPointsMax, List<CreditTransaction> transactions) {
        this.sessionId = sessionId;
        this.parent = parent;
        this.student = student;
        this.tutor = tutor;
        this.durationsHours = durationsHours;
        this.datetimeStarted = datetimeStarted;
        this.sessionStatus = sessionStatus;
        this.assessmentPointsEarned = assessmentPointsEarned;
        this.assessmentPointsGoal = assessmentPointsGoal;
        this.assessmentPointsMax = assessmentPointsMax;
        this.transactions = transactions;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Double getDurationsHours() {
        return durationsHours;
    }

    public void setDurationsHours(Double durationsHours) {
        this.durationsHours = durationsHours;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public LocalDateTime getDatetimeStarted() {
        return datetimeStarted;
    }

    public void setDatetimeStarted(LocalDateTime datetimeStarted) {
        this.datetimeStarted = datetimeStarted;
    }

    public Double getAssessmentPointsEarned() {
        return assessmentPointsEarned;
    }

    public void setAssessmentPointsEarned(Double assessmentPointsEarned) {
        this.assessmentPointsEarned = assessmentPointsEarned;
    }

    public Double getAssessmentPointsGoal() {
        return assessmentPointsGoal;
    }

    public void setAssessmentPointsGoal(Double assessmentPointsGoal) {
        this.assessmentPointsGoal = assessmentPointsGoal;
    }

    public Double getAssessmentPointsMax() {
        return assessmentPointsMax;
    }

    public void setAssessmentPointsMax(Double assessmentPointsMax) {
        this.assessmentPointsMax = assessmentPointsMax;
    }

    public List<CreditTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<CreditTransaction> transactions) {
        this.transactions = transactions;
    }


}
