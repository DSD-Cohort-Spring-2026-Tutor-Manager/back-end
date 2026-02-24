package org.tutortoise.service.session;

import java.math.BigDecimal;

public record SessionStudentData(
    String firstName,
    String lastName,
    Integer studentId,
    Integer rowNum,
    BigDecimal assessmentPointsEarned,
    BigDecimal assessmentPointsMax) {}
