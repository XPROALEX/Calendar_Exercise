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

    public Recurrence() {
    }

    public RecurrenceFormat getFrequency() {
        return frequency;
    }

    public void setFrequency(RecurrenceFormat frequency) {
        this.frequency = frequency;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
