package it.Ale.Calendar.event;

import it.Ale.Calendar.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EventRepository extends JpaRepository<Event, Long> {
    Iterable<Event> findByCalendarId(long calendarId);
}
