package org.tutortoise.service.session;


import java.sql.Timestamp;

public record SessionStudentSubjectData(
    Integer studentId,
    Integer subjectId,
    SessionStatus status,
    Timestamp datetime,
    String studentName,
    String subject,
    Integer totalSessionHours,
    Integer totalSessionsHoursCompleted) {}
