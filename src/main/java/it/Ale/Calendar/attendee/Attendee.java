package it.Ale.Calendar.attendee;


import it.Ale.Calendar.attendee.util.Status;
import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.event.Event;
import it.Ale.Calendar.user.User;
import it.Ale.Calendar.user.UserRepository;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User invitedUser;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

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

    public List<Attendee> inviteParticipants(long[] participantIdArray, UserRepository userRepository, Event event) {
        List<Attendee> attendeeList = new ArrayList<>();
        for (long participantId : participantIdArray) {
            User participant = userRepository.findById(participantId).get();
            Attendee attendee = new Attendee();
            attendee.setEvent(event);
            attendee.setInvitedUser(participant);
            attendeeList.add(attendee);
        }
        return attendeeList;
    }
}
//     for (int i = 0; i < participantsId.length; i++) {
//        User participant = userRepository.findById(participantsId[i]).get();
//        Attendee attendee = new Attendee();
//        attendee.setEvent(event);
//        attendee.setInvitedUser(participant);
//        attendeeRepository.save(attendee);
//        }