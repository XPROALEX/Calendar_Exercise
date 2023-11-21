package it.Ale.Calendar.event;

import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.calendar.CalendarRepository;
import it.Ale.Calendar.event.util.Recurrence;
import it.Ale.Calendar.user.AttendeeRepository;
import it.Ale.Calendar.user.User;
import it.Ale.Calendar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AttendeeRepository attendeeRepository;

    //Inviti per gli eventi da creare e implementare.
    public Event create(long userid, long calendarId, EventDto eventDto) {
        User user = userRepository.findById(userid).get();
        Optional<Calendar> calendarOptional = calendarRepository.findById(calendarId);
        if (calendarOptional.isEmpty()) {
            return null;
        }
        Calendar calendar = calendarOptional.get();
        if (eventDto.isRecurring() == false) {
            Event event = new Event();
            event.setRecurring(eventDto.isRecurring());
            event.setName(eventDto.getName());
            event.setDescription(eventDto.getDescription());
            event.setStart(eventDto.getStart());
            event.setEnd(eventDto.getEnd());
            event.getParticipants().add(user);
            event.setCalendar(calendar);
            calendar.getEvents().add(event);
            user.getEvents().add(event);
            eventRepository.save(event);
            return event;
        }
//        if (eventDto.isRecurring()) {
//            Recurrence recurrence = new Recurrence();
//            recurrence.recurrenceForDaysPattern(user, calendar, eventDto, eventRepository);
//        }
        return null;
    }

    public void deleteByid(long id) {
        eventRepository.deleteById(id);
    }

    public Optional<EventDto> findEventDtoById(long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            EventDto eventDto = new EventDto();
            eventDto.setName(event.get().getName());
            eventDto.setDescription(event.get().getDescription());
            eventDto.setStart(event.get().getStart());
            eventDto.setEnd(event.get().getEnd());
            eventDto.setCalendarName(event.get().getCalendar().getName());
            return Optional.of(eventDto);
        }
        return null;
    }

    public Optional<Event> findById(long id) {
        return eventRepository.findById(id);
    }
}
