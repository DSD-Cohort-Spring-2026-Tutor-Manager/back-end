package org.tutortoise.service.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Integer subjectId;
    private String subjectName;
    private String totalSubjectHours;
}
