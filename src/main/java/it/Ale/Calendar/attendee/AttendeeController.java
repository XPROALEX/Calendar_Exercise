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
    url /attendee/{eventId}
    Json
    {
        "participantsId": [1,2,3]
    }
     */

    @PostMapping("/invite/{eventId}")
    public ResponseEntity<?> invite(@PathVariable long eventId, @RequestBody AttendeeDto attendeeDto) {
        attendeeService.sendAttendee(eventId, attendeeDto);
        return ResponseEntity.ok().build();
    }
}
