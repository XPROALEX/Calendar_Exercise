package it.Ale.Calendar.service;

import it.Ale.Calendar.dto.EventDto;
import it.Ale.Calendar.entity.Event;
import it.Ale.Calendar.entity.User;
import it.Ale.Calendar.repository.CalendarRepository;
import it.Ale.Calendar.repository.EventRepository;
import it.Ale.Calendar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    UserRepository userRepository;

    public Event save(long userid, String calendarName, EventDto eventDto) {
        Optional<User> userOptional = userRepository.findById(userid);
        User user = userOptional.get();
        if (userOptional.isPresent()) {
            Event event = new Event();
            event.setName(eventDto.getName());
            event.setDescription(eventDto.getDescription());
            event.setStart(eventDto.getStart());
            event.setEnd(eventDto.getEnd());
            event.setCalendar(calendarRepository.findByname(calendarName).get());
            event.getUsers().add(user);
            eventRepository.save(event);

//            if (eventDto.getPartecipantsId() != null) {
//                for (int x = 0; x < eventDto.getPartecipantsId().length; x++) {
//                    Optional<User> partecipantOptional = userRepository.findById(eventDto.getPartecipantsId()[x]);
//                    if (partecipantOptional.isPresent()) {
//                        User partecipant = partecipantOptional.get();
//                        event.getUsers().add(partecipant);
//                    }
//                }
//            }
            return event;
        }
        return null;
    }

    public void deleteByid(long id) {
        eventRepository.deleteById(id);
    }

    public Optional<Event> findById(long id) {
        return eventRepository.findById(id);
    }

}

