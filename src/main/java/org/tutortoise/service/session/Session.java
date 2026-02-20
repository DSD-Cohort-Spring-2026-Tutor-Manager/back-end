package org.tutortoise.service.session;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.tutortoise.service.credit.CreditTransaction;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.student.Student;
import org.tutortoise.service.tutor.Tutor;

@Entity
@Table(name="session")
@Data
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

}
