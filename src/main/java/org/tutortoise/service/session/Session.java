package org.tutortoise.service.session;

import jakarta.persistence.*;
import lombok.Data;
import org.tutortoise.service.credit.CreditTransaction;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.student.Student;
import org.tutortoise.service.subject.Subject;
import org.tutortoise.service.tutor.Tutor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.tutortoise.service.session.SessionNamedQueries.FIND_STUDENT_INFO_BY_PARENT;
import static org.tutortoise.service.session.SessionNamedQueries.FIND_STUDENT_PROGRESS_BY_SUBJECT;

@Entity
@Table(name = "session")
@Data
@NamedNativeQueries({
  @NamedNativeQuery(
      name = "findStudentInfoByParent",
      query = FIND_STUDENT_INFO_BY_PARENT,
      resultSetMapping = "SessionStudentDataMapping"),
  @NamedNativeQuery(
      name = "studentProgressBySubject",
      query = FIND_STUDENT_PROGRESS_BY_SUBJECT,
      resultSetMapping = "SessionStudentSubjectDataMapping")
})
@SqlResultSetMappings({
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
              })),
  @SqlResultSetMapping(
      name = "SessionStudentSubjectDataMapping",
      classes =
          @ConstructorResult(
              targetClass = SessionStudentSubjectData.class,
              columns = {
                @ColumnResult(name = "studentId", type = Integer.class),
                @ColumnResult(name = "subjectId", type = Integer.class),
                @ColumnResult(name = "studentName", type = String.class),
                @ColumnResult(name = "subject", type = String.class),
                @ColumnResult(name = "totalSessionsHours", type = Integer.class),
                @ColumnResult(name = "totalSessionsHoursCompleted", type = Integer.class)
              }))
})
public class Session implements SessionNamedQueries {

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

  @OneToMany(
      mappedBy = "session",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<CreditTransaction> transactions = new ArrayList<>();
}
