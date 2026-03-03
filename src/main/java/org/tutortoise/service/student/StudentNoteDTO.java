package org.tutortoise.service.student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentNoteDTO {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private String notes;

    public StudentNoteDTO() {
    }

    public StudentNoteDTO(Integer studentId, String firstName, String notes, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.notes = notes;
        this.lastName = lastName;
    }
}
