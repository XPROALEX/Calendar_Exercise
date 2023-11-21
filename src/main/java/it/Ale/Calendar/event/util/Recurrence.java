package it.Ale.Calendar.event.util;

import it.Ale.Calendar.event.Event;
import it.Ale.Calendar.event.EventDto;
import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.event.EventRepository;
import it.Ale.Calendar.user.User;
import jakarta.persistence.Embeddable;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;


@Embeddable
public class Recurrence {
    //    @Enumerated(EnumType.STRING)
    private RecurrenceFormat frequency;

    //    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> days;

    private int count;

    public Recurrence() {
    }


    public RecurrenceFormat getFrequency() {
        return frequency;
    }

    public void setFrequency(RecurrenceFormat frequency) {
        this.frequency = frequency;
    }

    public Set<DayOfWeek> getDays() {
        return days;
    }

    public void setDays(Set<DayOfWeek> days) {
        this.days = days;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void recurrenceForDaysPattern(User user, Calendar calendar, EventDto eventDto, EventRepository eventRepository) {
        Recurrence recurring = eventDto.getRecurringDays();
        Set<DayOfWeek> days = recurring.getDays();
        days.forEach(day -> {
            int recurringCount = recurring.getCount() - 1;
            LocalDateTime start = eventDto.getStart();
            LocalDateTime end = eventDto.getEnd();
            while (recurringCount > 0) {
                Event recurringEvent = new Event();
                recurringEvent.setName(eventDto.getName());
                recurringEvent.setDescription(eventDto.getDescription());
                recurringEvent.setCalendar(calendar);
                calendar.getEvents().add(recurringEvent);
                user.getEvents().add(recurringEvent);
                eventRepository.save(recurringEvent);
                switch (recurring.getFrequency()) {
                    case DAILY:
                        if (days != null) {
                            recurringEvent.setStart(start.with(TemporalAdjusters.next(day)));
                            recurringEvent.setEnd(end.with(TemporalAdjusters.next(day)));
                            start = start.with(TemporalAdjusters.next(day));
                            end = end.with(TemporalAdjusters.next(day));
                        } else {
                            recurringEvent.setStart(start.plusDays(1));
                            recurringEvent.setEnd(end.plusDays(1));
                            start = start.plusDays(1);
                            end = end.plusDays(1);
                        }
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
                recurringCount--;
            }
        });
    }
}


//prima versione
//    public void recurrencePattern(User user, Calendar calendar, EventDto eventDto, EventRepository eventRepository) {
//        Recurrence recurring = eventDto.getRecurringDays();
//        int recurringCount = recurring.getCount() - 1;
//        DayOfWeek dayOfWeek = recurring.getDay();
//        LocalDateTime start = eventDto.getStart();
//        LocalDateTime end = eventDto.getEnd();
//        while (recurringCount > 0) {
//            Event recurringEvent = new Event();
//            recurringEvent.setName(eventDto.getName());
//            recurringEvent.setDescription(eventDto.getDescription());
//            switch (recurring.getFrequency()) {
//                case DAILY:
//                    if (dayOfWeek != null) {
//                        recurringEvent.setStart(start.with(TemporalAdjusters.next(dayOfWeek)));
//                        recurringEvent.setEnd(end.with(TemporalAdjusters.next(dayOfWeek)));
//                        start = start.with(TemporalAdjusters.next(dayOfWeek));
//                        end = end.with(TemporalAdjusters.next(dayOfWeek));
//                    } else {
//                        recurringEvent.setStart(start.plusDays(1));
//                        recurringEvent.setEnd(end.plusDays(1));
//                        start = start.plusDays(1);
//                        end = end.plusDays(1);
//                    }
//                    break;
//                case WEEKLY:
//                    recurringEvent.setStart(start.plusWeeks(1));
//                    recurringEvent.setEnd(end.plusWeeks(1));
//                    start = start.plusWeeks(1);
//                    end = end.plusWeeks(1);
//                    break;
//                case MONTHLY:
//                    recurringEvent.setStart(start.plusMonths(1));
//                    recurringEvent.setEnd(end.plusMonths(1));
//                    start = start.plusMonths(1);
//                    end = end.plusMonths(1);
//                    break;
//                case YEARLY:
//                    recurringEvent.setStart(start.plusYears(1));
//                    recurringEvent.setEnd(end.plusYears(1));
//                    start = start.plusYears(1);
//                    end = end.plusYears(1);
//            }
//            recurringEvent.getParticipants().add(user);
//            recurringEvent.setCalendar(calendar);
//            calendar.getEvents().add(recurringEvent);
//            user.getEvents().add(recurringEvent);
//            eventRepository.save(recurringEvent);
//            recurringCount--;
//        }
//    }
