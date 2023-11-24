package it.Ale.Calendar.attendee;

import it.Ale.Calendar.attendee.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Iterable<Attendee> findAllAttendeeByInvitedUser_Id(long userId);
    Iterable<Attendee>findAllAttendeeByStatus(Status status);
    Iterable<Attendee>findAllAttendeeByEventId(long eventId);
}
