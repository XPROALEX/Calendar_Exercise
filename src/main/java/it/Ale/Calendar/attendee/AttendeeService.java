package it.Ale.Calendar.attendee;

import it.Ale.Calendar.attendee.util.Status;
import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.event.Event;
import it.Ale.Calendar.event.EventDto;
import it.Ale.Calendar.event.EventRepository;
import it.Ale.Calendar.user.User;
import it.Ale.Calendar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AttendeeService {
    @Autowired
    AttendeeRepository attendeeRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;

    public void sendAttendee(long eventId, EventDto eventDto) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new IllegalArgumentException("Event not found");
        }
        if (eventDto.getParticipantsId() == null || eventDto.getParticipantsId().length == 0) {
            throw new IllegalArgumentException("Select users to invite");
        }
        Event event = eventRepository.findById(eventId).get();
        long[] participantsId = eventDto.getParticipantsId();
        List<Attendee> attendeeList = new Attendee().inviteParticipants(participantsId, userRepository,event);
        attendeeRepository.saveAll(attendeeList);
    }

    public void changeStatus(long attendeeId, Attendee attendeeStatus) {
        if (attendeeRepository.findById(attendeeId).isEmpty()) {
            throw new IllegalArgumentException("Attendee not found");
        }
        if (attendeeStatus.getStatus() == null ) {
            throw new IllegalArgumentException("Select status");
        }
        Attendee existingAttendee = attendeeRepository.findById(attendeeId).get();
        Event event = existingAttendee.getEvent();
        User user= existingAttendee.getInvitedUser();
        Calendar calendar=user.getCalendars().getFirst();
        Status statusChanged = attendeeStatus.getStatus();
        switch (statusChanged) {
            case PENDING:
                existingAttendee.setStatus(Status.PENDING);
                attendeeRepository.save(existingAttendee);
                break;
            case CONFIRMED:
                existingAttendee.setStatus(Status.CONFIRMED);
                event.getParticipants().add(user);
                user.getEvents().add(event);
                calendar.getEvents().add(event);
                userRepository.save(user);
                eventRepository.save(event);
                attendeeRepository.save(existingAttendee);
                break;
            case CANCELED:
                attendeeRepository.delete(existingAttendee);
                break;
        }
    }
}



