package org.tutortoise.service.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SessionDTO {

  private Integer sessionId;
  private Integer parentId;
  private Integer studentId;
  private String studentFirstName;
  private String studentLastName;
  private String notes;
  private String subject;
  private Integer tutorId;
  private String tutorName;
  private Double durationsHours;
  private String sessionStatus;
  private LocalDateTime datetimeStarted;
  private Double assessmentPointsEarned;
  private Double assessmentPointsGoal;
  private Double assessmentPointsMax;

  @Builder
  public SessionDTO() {}

  public static SessionDTO convertToDTO(Session session) {

    return new SessionDTO(
            session.getSessionId(),
            session.getParent() == null ? null : session.getParent().getParentId(),
            session.getStudent() == null ? null : session.getStudent().getStudentId(),
            session.getStudent() == null ? null :session.getStudent().getFirstName(),
            session.getStudent() == null ? null :session.getStudent().getLastName(),
            session.getStudent() == null ? null :session.getStudent().getNotes(),
            session.getSubject().getSubject(),
            session.getTutor().getTutorId(),
            session.getTutor().getFirstName() + " " + session.getTutor().getLastName(),
            session.getDurationsHours(),
            session.getSessionStatus().name(),
            session.getDatetimeStarted(),
            session.getAssessmentPointsEarned(),
            session.getAssessmentPointsGoal(),
            session.getAssessmentPointsMax());
  }
}
