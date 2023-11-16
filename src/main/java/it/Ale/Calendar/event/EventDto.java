package it.Ale.Calendar.event;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class EventDto {
    private String name;
    private String description;
    private String CalendarName;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime end;
    private boolean recurring;
    private long[] partecipantsId;
    private String[] recurrence;

    public EventDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public long[] getPartecipantsId() {
        return partecipantsId;
    }

    public void setPartecipantsId(long[] partecipantsId) {
        this.partecipantsId = partecipantsId;
    }

    public String getCalendarName() {
        return CalendarName;
    }

    public void setCalendarName(String calendarName) {
        CalendarName = calendarName;
    }

    public String[] getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String[] recurrence) {
        this.recurrence = recurrence;
    }
}
