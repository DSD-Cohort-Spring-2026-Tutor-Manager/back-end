package org.tutortoise.service.tutor;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TutorDTO {
    private Integer tutorId;
    private Integer sessionId;
    private Integer studentId;
    private Integer subjectId;
    private Integer assessmentPointEarned;
    private LocalDateTime dateTime;
    private Integer duration;
    private String tutorName;
    private String subject;
    private String studentName;

}
