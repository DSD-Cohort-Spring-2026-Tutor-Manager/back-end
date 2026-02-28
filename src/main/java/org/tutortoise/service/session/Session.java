package org.tutortoise.service.session;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
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
@AllArgsConstructor
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
  @JoinColumn(name = "parent_id_fk")
  private Parent parent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id_fk")
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

  public Session() {
  }


  public Integer getSessionId() {
    return sessionId;
  }

  public void setSessionId(Integer sessionId) {
    this.sessionId = sessionId;
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

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public List<CreditTransaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<CreditTransaction> transactions) {
    this.transactions = transactions;
  }
}
