package it.Ale.Calendar.event;

import it.Ale.Calendar.event.util.Recurrence;
import it.Ale.Calendar.user.User;
import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.user.AttendeeRepository;
import it.Ale.Calendar.calendar.CalendarRepository;
import it.Ale.Calendar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
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


    //    public Event create(long userid, long calendarId, EventDto eventDto) {
//        Optional<User> userOptional = userRepository.findById(userid);
//        User user = userOptional.get();
//        Event event = new Event();
//        event.setName(eventDto.getName());
//        event.setDescription(eventDto.getDescription());
//        event.setStart(eventDto.getStart());
//        event.setEnd(eventDto.getEnd());
////      event.setParticipants(eventDto.getPartecipantsId());
//        event.getParticipants().add(user);
//        Calendar calendar = calendarRepository.findById(calendarId).get();
//        event.setCalendar(calendar);
//        calendar.getEvents().add(event);
//        user.getEvents().add(event);
//        return eventRepository.save(event);
//    }
    public Event create(long userid, long calendarId, EventDto eventDto) {
        Optional<User> userOptional = userRepository.findById(userid);
        User user = userOptional.get();
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setStart(eventDto.getStart());
        event.setEnd(eventDto.getEnd());
        event.getParticipants().add(user);
        Calendar calendar = calendarRepository.findById(calendarId).get();
        event.setCalendar(calendar);
        calendar.getEvents().add(event);
        user.getEvents().add(event);
        if (eventDto.isRecurring()) {
            Recurrence recurring = event.getRecurringDays();
            int recurringCount = recurring.getCount();
            LocalDateTime start = eventDto.getStart();
            LocalDateTime end = eventDto.getEnd();
            DayOfWeek dayOfWeek = recurring.getDay();
            while (recurringCount > 0) {
                Event eventRecurring = new Event();
                eventRecurring.setName(event.getName());
                eventRecurring.setDescription(event.getDescription());

                switch (recurring.getFrequency()) {
                    case DAILY:
                        if (dayOfWeek != null) {
                            eventRecurring.setStart(start.with(TemporalAdjusters.next(dayOfWeek)));
                        } else
                            eventRecurring.setStart(start.plusDays(1));
                        break;
                    case WEEKLY:
                        eventRecurring.setStart(start.plusWeeks(1));
                        break;
                    case MONTHLY:
                        eventRecurring.setStart(start.plusMonths(1));
                        break;

                    case YEARLY:
                        eventRecurring.setStart(start.plusYears(1));
                }

                switch (recurring.getFrequency()) {
                    case DAILY:
                        if (dayOfWeek != null) {
                            eventRecurring.setEnd(end.with(TemporalAdjusters.next(dayOfWeek)));
                        } else
                            eventRecurring.setEnd(end.plusDays(1));
                        break;
                    case WEEKLY:
                        eventRecurring.setEnd(end.plusWeeks(1));
                        break;
                    case MONTHLY:
                        eventRecurring.setEnd(end.plusMonths(1));
                        break;
                    case YEARLY:
                        eventRecurring.setEnd(end.plusYears(1));
                }

                eventRecurring.getParticipants().add(user);
                eventRecurring.setCalendar(calendar);
                calendar.getEvents().add(eventRecurring);
                user.getEvents().add(eventRecurring);
                eventRepository.save(eventRecurring);
                recurringCount--;
            }
        }
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
            eventDto.setCalendarName(event.get().getCalendar().getName());
            return Optional.of(eventDto);
        }
        return null;
    }

    public Optional<Event> findById(long id) {
        return eventRepository.findById(id);
    }
}