package it.Ale.Calendar.service;

import it.Ale.Calendar.dto.EventDto;
import it.Ale.Calendar.entity.Attendee;
import it.Ale.Calendar.entity.Event;
import it.Ale.Calendar.entity.User;
import it.Ale.Calendar.enums.Status;
import it.Ale.Calendar.repository.AttendeeRepository;
import it.Ale.Calendar.repository.CalendarRepository;
import it.Ale.Calendar.repository.EventRepository;
import it.Ale.Calendar.repository.UserRepository;
import jakarta.transaction.Transactional;
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


    public Event create(long userid, long calendarId, Event eventInsert) {
        Optional<User> userOptional = userRepository.findById(userid);
//        if (userOptional.isEmpty()) {
//            return null;
//        }
        User user = userOptional.get();
        Event event = new Event();
        event.setName(eventInsert.getName());
        event.setDescription(eventInsert.getDescription());
        event.setStart(eventInsert.getStart());
        event.setEnd(eventInsert.getEnd());
        event.setParticipants(eventInsert.getParticipants());
        event.setCalendar(calendarRepository.findById(calendarId).get());
        return eventRepository.save(event);
    }

    public void deleteByid(long id) {
        eventRepository.deleteById(id);
    }

    public Optional<Event> findById(long id) {
        return eventRepository.findById(id);
    }
}
