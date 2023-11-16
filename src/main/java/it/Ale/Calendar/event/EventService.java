package it.Ale.Calendar.event;

import it.Ale.Calendar.user.User;
import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.user.AttendeeRepository;
import it.Ale.Calendar.calendar.CalendarRepository;
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


    public Event create(long userid, long calendarId, EventDto eventDto) {
        Optional<User> userOptional = userRepository.findById(userid);
        User user = userOptional.get();
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setStart(eventDto.getStart());
        event.setEnd(eventDto.getEnd());
//      event.setParticipants(eventDto.getPartecipantsId());
        event.getParticipants().add(user);
        Calendar calendar = calendarRepository.findById(calendarId).get();
        event.setCalendar(calendar);
        calendar.getEvents().add(event);
        user.getEvents().add(event);
        return eventRepository.save(event);
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
            eventDto.setRecurrence(event.get().getRecurrence());
            eventDto.setCalendarName(event.get().getCalendar().getName());
            return Optional.of(eventDto);
        }
        return null;

    }

    public Optional<Event> findById(long id) {
        return eventRepository.findById(id);
    }
}
