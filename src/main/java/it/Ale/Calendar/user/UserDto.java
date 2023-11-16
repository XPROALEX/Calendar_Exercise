package it.Ale.Calendar.user;

import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.event.Event;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private String name;
    private String email;
    private Set<Calendar> calendars;
    private Set<ContactDto> contacts = new HashSet<>();
    private Set<Event> events = new HashSet<>();

    public UserDto() {
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

    public Set<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(Set<Calendar> calendars) {
        this.calendars = calendars;
    }

    public Set<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactDto> contacts) {
        this.contacts = contacts;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

}
