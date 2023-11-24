package it.Ale.Calendar.attendee;

import it.Ale.Calendar.attendee.util.AttendeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendee")
public class AttendeeController {
    @Autowired
    AttendeeService attendeeService;
    /*
    Post invite to an event
    url /attendee/event/{eventId}
    Json
    {
        "participantsId": [1,2,3]
    }
     */

    @PostMapping("/event/{eventId}")
    public ResponseEntity<?> invite(@PathVariable long eventId, @RequestBody AttendeeDto attendeeDto) {
        attendeeService.sendAttendee(eventId, attendeeDto);
        return ResponseEntity.ok().build();
    }
    /*
    Put change status
    url /attendee/{attendeeId}
    Json
    {
        "status": "CONFIRMED", "status": "CANCELED", "status": "PENDING"
    }
     */

    @PutMapping ("/{attendeeId}")
    public ResponseEntity<?> changeStatus(@PathVariable long attendeeId, @RequestBody Attendee attendeeStatus){
        attendeeService.changeStatus(attendeeId, attendeeStatus);
        return ResponseEntity.ok().build();
    }
}
