package it.Ale.Calendar.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime start;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime end;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;
    private String[] recurrence;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<>();


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

    public Set<User> getUsers() {
        return participants;
    }

    public void setUsers(Set<User> users) {
        this.participants = users;
    }

    public void addParticipant(User user) {
        this.participants.add(user);
        user.getEvents().add(this);
    }

}
