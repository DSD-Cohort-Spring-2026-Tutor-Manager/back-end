package org.tutortoise.service.session;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionDTO {

  private Integer sessionId;
  private Integer parentId;
  private Integer studentId;
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
                session.getParent().getParentId(),
                session.getStudent().getStudentId(),
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
