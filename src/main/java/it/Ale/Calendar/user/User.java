package it.Ale.Calendar.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.Ale.Calendar.attendee.Attendee;
import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.event.Event;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
//    @Email(message = "Invalid email")
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calendar> calendars = new LinkedList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "participants", cascade = CascadeType.ALL)
    private Set<Event> events = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_contacts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private Set<User> contacts;

    @JsonIgnore
    @OneToMany(mappedBy = "invitedUser",cascade = CascadeType.ALL)
    private Set<Attendee> attendees = new HashSet<>();

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<User> getContacts() {
        return contacts;
    }

    public void setContacts(Set<User> contacts) {
        this.contacts = contacts;
    }
}

