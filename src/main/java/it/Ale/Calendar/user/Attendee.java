package it.Ale.Calendar.user;


import it.Ale.Calendar.event.Event;
import it.Ale.Calendar.event.util.Status;
import jakarta.persistence.*;


@Entity
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User invitedUser;
    @Enumerated(EnumType.STRING)
    private Status status= Status.PENDING;

    public Attendee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(User user) {
        this.invitedUser = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}