package it.Ale.Calendar.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository

public interface EventRepository extends JpaRepository<Event, Long> {
    Iterable<Event> findEventByCalendarId(long calendarId);

    Iterable<Event> findEventByCalendarIdAndStartBetween(long calendarId, LocalDateTime start, LocalDateTime end);
}
