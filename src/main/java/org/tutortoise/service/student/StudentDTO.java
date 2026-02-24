package org.tutortoise.service.student;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.tutortoise.service.session.SessionDTO;

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
  private List<SessionDTO> sessions = new ArrayList<>();
}
