package org.tutortoise.service.student;

import lombok.Builder;
import lombok.Data;
import org.tutortoise.service.session.SessionDTO;
import org.tutortoise.service.subject.SubjectDTO;

import java.util.List;

@Data
@Builder
public class StudentDTO {

    private Integer studentId;
    private Integer parentId;
    private String studentName;
    private String notes;
    private Integer sessionsCompleted;
    private Integer previousScore;
    private Integer latestScore;
    private List<SessionDTO> sessions;
    private List<SubjectDTO> subjects;
}
