package org.tutortoise.service.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {
    private Integer subjectId;
    private String subjectName;
    private Integer totalSubjectHours;
    private Integer totalSubjectHoursCompleted;
    private Double progressPercentage;
}
