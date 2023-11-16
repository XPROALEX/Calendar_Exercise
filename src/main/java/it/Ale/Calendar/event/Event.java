package it.Ale.Calendar.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.event.util.Recurrence;
import it.Ale.Calendar.event.util.Status;
import it.Ale.Calendar.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime start;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime end;

    private boolean recurring = false;

    @Enumerated(EnumType.STRING)
    private Recurrence recurringDays;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;


    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }


    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public Recurrence getRecurringDays() {
        return recurringDays;
    }

    public void setRecurringDays(Recurrence recurringDays) {
        this.recurringDays = recurringDays;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void inviteParticipant(User user) {
        this.participants.add(user);
        user.getEvents().add(this);
    }
}
