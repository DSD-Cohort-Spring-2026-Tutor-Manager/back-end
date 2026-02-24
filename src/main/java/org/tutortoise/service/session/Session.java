package org.tutortoise.service.session;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.tutortoise.service.credit.CreditTransaction;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.student.Student;
import org.tutortoise.service.subject.Subject;
import org.tutortoise.service.tutor.Tutor;

@NamedNativeQuery(
    name = "findStudentInfoByParent",
    query =
        "WITH StudentDateTime AS ("
            + "       SELECT "
            + "        *, ROW_NUMBER() OVER ( "
            + "            PARTITION BY session.parent_id_fk, session.student_id_fk, session.subject_id_fk "
            + "            ORDER BY session.datetime_started DESC "
            + "            ) as rowNum "
            + "    FROM "
            + "        session "
            + "    WHERE session.session_status = 'completed' AND session.parent_id_fk = :parentId "
            + ")"
            + "SELECT "
            + "stu.first_name as firstName,"
            + "stu.last_name as lastName,"
            + "sdt.student_id_fk as studentId, "
            + "sdt.rowNum as rowNum, "
            + "sum(sdt.assessment_points_earned) as assessmentPointsEarned, "
            + "sum(sdt.assessment_points_max) as assessmentPointsMax"
            + " FROM "
            + "    StudentDateTime sdt, "
            + "    student stu "
            + "WHERE "
            + "    rowNum <= 2 "
            + "AND sdt.student_id_fk = stu.student_id "
            + "GROUP BY stu.first_name, stu.last_name, sdt.student_id_fk, sdt.rowNum "
            + "order by sdt.rowNum, sdt.student_id_fk",
    resultSetMapping = "SessionStudentDataMapping")
@SqlResultSetMapping(
    name = "SessionStudentDataMapping",
    classes =
        @ConstructorResult(
            targetClass = SessionStudentData.class,
            columns = {
              @ColumnResult(name = "firstName", type = String.class),
              @ColumnResult(name = "lastName", type = String.class),
              @ColumnResult(name = "studentId", type = Integer.class),
              @ColumnResult(name = "rowNum", type = Integer.class),
              @ColumnResult(name = "assessmentPointsEarned", type = BigDecimal.class),
              @ColumnResult(name = "assessmentPointsMax", type = BigDecimal.class)
            }))
@Entity
@Table(name = "session")
@Data
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "session_id")
  private Integer sessionId;

  @Column(name = "duration_hours")
  private Double durationsHours;

  @Enumerated(EnumType.STRING)
  @Column(name = "session_status")
  private SessionStatus sessionStatus;

  @Column(name = "datetime_started")
  private LocalDateTime datetimeStarted;

  @Column(name = "assessment_points_earned")
  private Double assessmentPointsEarned;

  @Column(name = "assessment_points_goal")
  private Double assessmentPointsGoal;

  @Column(name = "assessment_points_max")
  private Double assessmentPointsMax;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id_fk", nullable = false)
  private Parent parent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id_fk", nullable = false)
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tutor_id_fk", nullable = false)
  private Tutor tutor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subject_id_fk", nullable = false)
  private Subject subject;

  @OneToMany(mappedBy = "session", fetch =  FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CreditTransaction> transactions = new ArrayList<>();
}
