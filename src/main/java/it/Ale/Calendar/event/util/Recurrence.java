package it.Ale.Calendar.event.util;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.DayOfWeek;

public class Recurrence {
    @Enumerated(EnumType.STRING)
    private RecurrenceFormat frequency;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    private int count;
}
