package org.tutortoise.service.tutor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TutorSessionRequest {

    @Positive(message = "Tutor id must be positive integer")
    private Integer tutorId;

    @Positive(message = "Session id must be positive integer")
    private Integer sessionId;

    @Positive(message = "Tutor id must be positive integer")
    @Min(value = 0)
    @Max(value = 100)
    private Integer grade;
}
