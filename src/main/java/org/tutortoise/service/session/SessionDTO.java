package org.tutortoise.service.session;

import java.time.LocalDateTime;

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

    public SessionDTO() {
    }

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


    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTutorId() {
        return tutorId;
    }

    public void setTutorId(Integer tutorId) {
        this.tutorId = tutorId;
    }

    public Double getDurationsHours() {
        return durationsHours;
    }

    public void setDurationsHours(Double durationsHours) {
        this.durationsHours = durationsHours;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
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
}
