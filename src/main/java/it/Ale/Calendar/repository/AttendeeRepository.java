package it.Ale.Calendar.repository;

import it.Ale.Calendar.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
}
