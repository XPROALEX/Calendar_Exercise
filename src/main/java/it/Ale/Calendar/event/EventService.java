package it.Ale.Calendar.event;

import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.calendar.CalendarRepository;
import it.Ale.Calendar.event.util.Recurrence;
import it.Ale.Calendar.attendee.AttendeeRepository;
import it.Ale.Calendar.user.User;
import it.Ale.Calendar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
    public void create(long userid, long calendarId, EventDto eventDto) {
        User user = userRepository.findById(userid).get();
        Calendar calendar = calendarRepository.findById(calendarId).get();
        if (eventDto.isRecurring()) {
            Recurrence recurrence = new Recurrence();
            recurrence.recurrencePattern(user, calendar, eventDto, eventRepository);
        } else if (eventDto.isRecurring() == false) {
            Event event = new Recurrence().createEvent(user, calendar, eventDto);
            event.setStart(eventDto.getStart());
            event.setEnd(eventDto.getEnd());
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

    /*
    Return all events for a calendar
     */
    public Iterable<Event> findAllByCalendar(long calendarId) {
        return eventRepository.findEventByCalendarIdOrderByStartAsc(calendarId);
    }

    /*
    Return all events for a calendar between two date
     */
    public Iterable<Event> findAllByCalendarBetweenTwoDate(long calendarId, String startStr, String endStr) {
        LocalDateTime start = LocalDate.parse(startStr).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endStr).plusDays(1).atStartOfDay();
        return eventRepository.findEventByCalendarIdAndStartBetweenOrderByStartAsc(calendarId, start, end);
    }
    /*
    Return all events for a calendar today
     */

    public Iterable<Event> findAllByCalendarToday(long calendarId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        return eventRepository.findEventByCalendarIdAndStartBetweenOrderByStartAsc(calendarId, start, end);
    }
    /*
    Return all events for a calendar this week
     */

    public Iterable<Event> findAllByCalendarInThiWeek(long calendarId) {
        LocalDate now = LocalDate.now();
        LocalDateTime start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime end = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1).atStartOfDay();
        return eventRepository.findEventByCalendarIdAndStartBetweenOrderByStartAsc(calendarId, start, end);
    }

    /*
    Return all events for a calendar this month
     */
    public Iterable<Event> findAllByCalendarInThisMonth(long calendarId) {
        LocalDateTime start = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime end = YearMonth.now().atEndOfMonth().plusDays(1).atStartOfDay();
        return eventRepository.findEventByCalendarIdAndStartBetweenOrderByStartAsc(calendarId, start, end);
    }

    /*
    Return all events for participants
     */
    public Iterable<Event> findAllByParticipantId(long ParticipantId) {
        return eventRepository.findEventByParticipantsIdOrderByStartAsc(ParticipantId);
    }

    /*
    return all events for participants between two date
     */
    public Iterable<Event> findAllByParticipantIdBetweenTwoDate(long ParticipantId, String startStr, String endStr) {
        LocalDateTime start = LocalDate.parse(startStr).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endStr).plusDays(1).atStartOfDay();
        return eventRepository.findEventByParticipantsIdAndStartBetweenOrderByStartAsc(ParticipantId, start, end);
    }

    /*
    Return all events for participants today
     */
    public Iterable<Event> findAllByParticipantIdToday(long ParticipantId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        return eventRepository.findEventByParticipantsIdAndStartBetweenOrderByStartAsc(ParticipantId, start, end);
    }

    /*
    Return all events for participants this week
     */
    public Iterable<Event> findAllByParticipantIdInThiWeek(long ParticipantId) {
        LocalDate now = LocalDate.now();
        LocalDateTime start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime end = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1).atStartOfDay();
        return eventRepository.findEventByParticipantsIdAndStartBetweenOrderByStartAsc(ParticipantId, start, end);
    }

    /*
    Return all events for participants this month
     */
    public Iterable<Event> findAllByParticipantIdInThisMonth(long ParticipantId) {
        LocalDateTime start = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime end = YearMonth.now().atEndOfMonth().plusDays(1).atStartOfDay();
        return eventRepository.findEventByParticipantsIdAndStartBetweenOrderByStartAsc(ParticipantId, start, end);
    }

}
