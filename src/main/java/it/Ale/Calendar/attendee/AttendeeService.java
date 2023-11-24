package it.Ale.Calendar.attendee;

import it.Ale.Calendar.attendee.util.AttendeeDto;
import it.Ale.Calendar.attendee.util.Status;
import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.calendar.CalendarRepository;
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
    @Autowired
    CalendarRepository calendarRepository;

    public void sendAttendee(long eventId, AttendeeDto attendeeDto) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new IllegalArgumentException("Event not found");
        }
        if (attendeeDto.getParticipantsId() == null || attendeeDto.getParticipantsId().length == 0) {
            throw new IllegalArgumentException("Select users to invite");
        }
        Event event = eventRepository.findById(eventId).get();
        List<Attendee> attendeeList = new Attendee().inviteParticipants(attendeeDto, userRepository, event);
        attendeeRepository.saveAll(attendeeList);
    }

    public void changeStatus(long attendeeId, Attendee attendeeStatus) {
        if (attendeeStatus.getStatus() == null) {
            throw new IllegalArgumentException("Select status");
        }
        Attendee existingAttendee = attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new IllegalArgumentException("Attendee not found"));
        Event event = existingAttendee.getEvent();
        User user = existingAttendee.getInvitedUser();
        Calendar calendar = user.getCalendars().getFirst();
        Status statusChanged = attendeeStatus.getStatus();
        switch (statusChanged) {
            case PENDING:
                existingAttendee.setStatus(Status.PENDING);
                break;
            case CONFIRMED:
                existingAttendee.setStatus(Status.CONFIRMED);
                event.getParticipants().add(user);
                user.getEvents().add(event);
                calendar.getEvents().add(event);
                userRepository.save(user);
                eventRepository.save(event);
                attendeeRepository.save(existingAttendee);
                calendarRepository.save(calendar);
                break;
            case CANCELED:
                attendeeRepository.delete(existingAttendee);
                break;
        }
    }
}



