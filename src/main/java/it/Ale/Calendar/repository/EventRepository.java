package it.Ale.Calendar.repository;

import it.Ale.Calendar.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
