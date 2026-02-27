package org.tutortoise.service.session;


public record SessionStudentSubjectData(
    Integer studentId,
    Integer subjectId,
    String studentName,
    String subject,
    Integer totalSessionsHours,
    Integer totalSessionsHoursCompleted) {}
