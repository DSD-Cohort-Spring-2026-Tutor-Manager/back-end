package org.tutortoise.service.parent;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.tutortoise.service.advice.HttpRestResponse;
import org.tutortoise.service.student.StudentDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentDTO extends HttpRestResponse {

    private Integer parentId;
    private String parentName;
    private String parentEmail;
    private Integer sessionCount;
    private double creditBalance;
    private List<StudentDTO> students = new ArrayList<>();

}
