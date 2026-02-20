package org.tutortoise.service.session;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SessionDTO {

    private Integer sessionId;
    private Integer parentId;
    private Integer studentId;
    private Integer tutorId;
    private Double durationsHours;
    private String sessionStatus;
    private LocalDateTime datetimeStarted;
    private Double assessmentPointsEarned;
    private Double assessmentPointsGoal;
    private Double assessmentPointsMax;


    public SessionDTO(Integer sessionId, Integer parentId, Integer studentId, Integer tutorId, String sessionStatus, Double durationsHours, LocalDateTime datetimeStarted, Double assessmentPointsEarned, Double assessmentPointsGoal, Double assessmentPointsMax) {
        this.sessionId = sessionId;
        this.parentId = parentId;
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.sessionStatus = sessionStatus;
        this.durationsHours = durationsHours;
        this.datetimeStarted = datetimeStarted;
        this.assessmentPointsEarned = assessmentPointsEarned;
        this.assessmentPointsGoal = assessmentPointsGoal;
        this.assessmentPointsMax = assessmentPointsMax;
    }

    public static SessionDTO convertToDTO(Session session) {
        return new SessionDTO(session.getSessionId(),
                session.getParent().getParentId(),
                session.getStudent().getStudentId(),
                session.getTutor().getTutorId(),
                session.getSessionStatus().name(),
                session.getDurationsHours(),
                session.getDatetimeStarted(),
                session.getAssessmentPointsEarned(),
                session.getAssessmentPointsGoal(),
                session.getAssessmentPointsMax());
    }
}
