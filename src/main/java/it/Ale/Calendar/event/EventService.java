package it.Ale.Calendar.event;

import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.calendar.CalendarRepository;
import it.Ale.Calendar.event.util.Recurrence;
import it.Ale.Calendar.user.AttendeeRepository;
import it.Ale.Calendar.user.User;
import it.Ale.Calendar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public void create(long userid, long calendarId, EventDto eventDto) {
        User user = userRepository.findById(userid).get();
        Calendar calendar = calendarRepository.findById(calendarId).get();
        if (eventDto.isRecurring()) {
            Recurrence recurrence = new Recurrence();
            recurrence.recurrenceForDaysPattern(user, calendar, eventDto, eventRepository);
        } else if (eventDto.isRecurring() == false) {
            Event event = new Event();
            event.setRecurring(eventDto.isRecurring());
            event.setName(eventDto.getName());
            event.setDescription(eventDto.getDescription());
            event.setStart(eventDto.getStart());
            event.setEnd(eventDto.getEnd());
            event.getParticipants().add(user);
            event.setCalendar(calendar);
            event.setRecurring(eventDto.isRecurring());
            calendar.getEvents().add(event);
            user.getEvents().add(event);
            eventRepository.save(event);
        }
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

    public Iterable<Event> findAllByCalendar(long calendarId) {
        return eventRepository.findEventByCalendarId(calendarId);
    }

    public Iterable<Event> findAllByCalendarAndStartBetween(long calendarId, String startStr, String endStr) {
        LocalDateTime start = LocalDate.parse(startStr).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endStr).plusDays(1).atStartOfDay();
        return eventRepository.findEventByCalendarIdAndStartBetween(calendarId, start, end);
    }

    public Iterable<Event> findAllByCalendarToday(long calendarId) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        return eventRepository.findEventByCalendarIdAndStartBetween(calendarId, start, end);
    }
}
