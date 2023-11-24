package it.Ale.Calendar.event.util;

import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.event.Event;
import it.Ale.Calendar.event.EventDto;
import it.Ale.Calendar.event.EventRepository;
import it.Ale.Calendar.user.User;
import jakarta.persistence.Embeddable;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;


@Embeddable
public class Recurrence {
    //    @Enumerated(EnumType.STRING)
    private RecurrenceFormat frequency;

    //    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> days = new ArrayList<>();

    private int count;

    public Recurrence() {
    }


    public RecurrenceFormat getFrequency() {
        return frequency;
    }

    public void setFrequency(RecurrenceFormat frequency) {
        this.frequency = frequency;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    public void setDays(List<DayOfWeek> days) {
        this.days = days;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /*
    recurrenceForDaysPattern
    days: [MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY]
    frequency: DAILY
    only daily repetitions without filtering for the days of the week
    frequency: WEEKLY
    weekly repetition with counter , repetitions fore days of the week with weeks counter .
    e.g. every Monday,Tuesday and Wednesday for 4 weeks .
    frequency: MONTHLY
    only monthly repetitions without filtering for the days of the week
    frequency: YEARLY
    only yearly repetitions without filtering for the days of the week
    ----------------------------------------------------------------------------------------------------------
    start and end have "minusDays(1)" only to avoid problems with the creation of weekly events that exceeded days,
    when start and end are then added to events those days are restored
     */
    public void recurrencePattern(User user, Calendar calendar, EventDto eventDto, EventRepository eventRepository) {
        Recurrence recurring = eventDto.getRecurringDays();
        LocalDateTime start = eventDto.getStart().minusDays(1);
        LocalDateTime end = eventDto.getEnd().minusDays(1);
        int recurringCount = recurring.getCount();
        switch (recurring.getFrequency()) {
            case DAILY:
                while (recurringCount > 0) {
                    Event recurringEvent = createEvent(user, calendar, eventDto);
                    recurringEvent.setStart(start.plusDays(1));
                    recurringEvent.setEnd(end.plusDays(1));
                    eventRepository.save(recurringEvent);
                    start = start.plusDays(1);
                    end = end.plusDays(1);
                    recurringCount--;
                }
                break;
            case WEEKLY:
                if (!recurring.getDays().isEmpty()) {
                    List<DayOfWeek> dayOfWeekSet = recurring.getDays();
                    while (recurringCount > 0) {
                        for (DayOfWeek day : dayOfWeekSet) {
                            Event recurringEvent = createEvent(user, calendar, eventDto);
                            recurringEvent.setDescription(eventDto.getDescription());
                            recurringEvent.setStart(start.with(TemporalAdjusters.nextOrSame(day)));
                            recurringEvent.setEnd(end.with(TemporalAdjusters.nextOrSame(day)));
                            eventRepository.save(recurringEvent);
                            start = start.with(TemporalAdjusters.next(day));
                            end = end.with(TemporalAdjusters.next(day));
                        }
                        recurringCount--;
                    }
                } else
                    while (recurringCount > 0) {
                        Event recurringEvent = createEvent(user, calendar, eventDto);
                        recurringEvent.setStart(start.plusDays(1));
                        recurringEvent.setEnd(end.plusDays(1));
                        eventRepository.save(recurringEvent);
                        start = start.plusWeeks(1);
                        end = end.plusWeeks(1);
                        recurringCount--;
                    }
                break;
            case MONTHLY:
                while (recurringCount > 0) {
                    Event recurringEvent = createEvent(user, calendar, eventDto);
                    recurringEvent.setStart(start.plusDays(1));
                    recurringEvent.setEnd(end.plusDays(1));
                    eventRepository.save(recurringEvent);
                    start = start.plusMonths(1);
                    end = end.plusMonths(1);
                    recurringCount--;
                }
                break;
            case YEARLY:
                while (recurringCount > 0) {
                    Event recurringEvent = createEvent(user, calendar, eventDto);
                    recurringEvent.setStart(start.plusDays(1));
                    recurringEvent.setEnd(end.plusDays(1));
                    eventRepository.save(recurringEvent);
                    start = start.plusYears(1);
                    end = end.plusYears(1);
                    recurringCount--;
                }
        }
    }

    public Event createEvent(User user, Calendar calendar, EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.getParticipants().add(user);
        event.setCalendar(calendar);
        event.setRecurring(eventDto.isRecurring());
        event.setRecurringDays(eventDto.getRecurringDays());
        calendar.getEvents().add(event);
        user.getEvents().add(event);
        return event;
    }
}

