package org.tutortoise.service.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProgressDTO {
    private Integer subjectId;
    private String subjectName;
    private Integer totalSubjectHours;
    private Integer subjectHoursUsed;
    private Double progressPercentage;
}
