package it.Ale.Calendar.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.Ale.Calendar.event.util.Recurrence;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class EventDto {
    private String name;
    private String description;
    private String CalendarName;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime end;
    private boolean recurring =false;
    private long[] participantsId;

    @Enumerated(EnumType.STRING)
    private Set<Recurrence> recurringDays = new HashSet<>();


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

    public long[] getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(long[] participantsId) {
        this.participantsId = participantsId;
    }

    public String getCalendarName() {
        return CalendarName;
    }

    public void setCalendarName(String calendarName) {
        CalendarName = calendarName;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public Set<Recurrence> getRecurringDays() {
        return recurringDays;
    }

    public void setRecurringDays(Set<Recurrence> recurringDays) {
        this.recurringDays = recurringDays;
    }
}
