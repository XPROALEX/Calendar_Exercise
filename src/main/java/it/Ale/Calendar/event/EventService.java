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

    //Inviti per gli eventi da creare e implementare.
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
            Recurrence recurring = eventDto.getRecurringDays();
            int recurringCount = recurring.getCount();
            DayOfWeek dayOfWeek = recurring.getDay();
            LocalDateTime start = eventDto.getStart();
            LocalDateTime end = eventDto.getEnd();
            while (recurringCount > 0) {
                Event recurringEvent = new Event();
                recurringEvent.setName(eventDto.getName());
                recurringEvent.setDescription(eventDto.getDescription());
                switch (recurring.getFrequency()) {
                    case DAILY:
                        recurringEvent.setStart(start.with(TemporalAdjusters.next(dayOfWeek)));
                        recurringEvent.setEnd(end.with(TemporalAdjusters.next(dayOfWeek)));
                        start = start.with(TemporalAdjusters.next(dayOfWeek));
                        end = end.with(TemporalAdjusters.next(dayOfWeek));
                        break;
                    case WEEKLY:
                        recurringEvent.setStart(start.plusWeeks(1));
                        recurringEvent.setEnd(end.plusWeeks(1));
                        start = start.plusWeeks(1);
                        end = end.plusWeeks(1);
                        break;
                    case MONTHLY:
                        recurringEvent.setStart(start.plusMonths(1));
                        recurringEvent.setEnd(end.plusMonths(1));
                        start = start.plusMonths(1);
                        end = end.plusMonths(1);
                        break;
                    case YEARLY:
                        recurringEvent.setStart(start.plusYears(1));
                        recurringEvent.setEnd(end.plusYears(1));
                        start = start.plusYears(1);
                        end = end.plusYears(1);
                }

                recurringEvent.getParticipants().add(user);
                recurringEvent.setCalendar(calendar);
                calendar.getEvents().add(recurringEvent);
                user.getEvents().add(recurringEvent);
                eventRepository.save(recurringEvent);
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
