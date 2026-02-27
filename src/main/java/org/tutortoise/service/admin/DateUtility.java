package org.tutortoise.service.admin;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

public class DateUtility {

    public static LocalDateTime getStartOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay();
    }

    public static LocalDateTime getEndOfWeek() {
        return getStartOfWeek()
                .plusDays(6)
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
    }
}
