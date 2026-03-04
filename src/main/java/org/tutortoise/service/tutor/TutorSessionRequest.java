package org.tutortoise.service.tutor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TutorSessionRequest {

    @NotNull(message = "Tutor id is required")
    @Positive(message = "Tutor id must be a positive integer")
    private Integer tutorId;

    @NotNull(message = "Session id is required")
    @Positive(message = "Session id must be a positive integer")
    private Integer sessionId;

    @Min(value = 0, message = "Grade must be between 0 and 100")
    private int grade;
}
