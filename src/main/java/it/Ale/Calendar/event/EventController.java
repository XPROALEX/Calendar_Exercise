package it.Ale.Calendar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;

    /*
    Post Create Event
    url /user/{userId}/calendar/{calendarId}
    Json
      {
        "name": "eventName",
        "description": "eventDescription",
        "start": "16/11/2023 10:00",
        "end": "16/11/2023 11:30",
        "recurring": true or false,
        "participantsId": [1, 2, 3],
        "recurringDays": {
          "frequency": "DAILY","WEEKLY","MONTHLY","YEARLY",
          "days": "MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY",
          "count": 5
        }
      }
     */
    @PostMapping("user/{userId}/calendar/{calendarId}")
    public ResponseEntity<?> createEvent(@PathVariable long userId, @PathVariable long calendarId, @RequestBody EventDto eventDto) {
        eventService.create(userId, calendarId, eventDto);
        return ResponseEntity.ok().body(eventDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (eventService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteByid(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (eventService.findEventDtoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(eventService.findEventDtoById(id));
    }

    @GetMapping("calendar/{calendarId}")
    public ResponseEntity<?> findAllByCalendar(@PathVariable Long calendarId) {
        return ResponseEntity.ok().body(eventService.findAllByCalendar(calendarId));
    }

    /*
    get Events By date
    url /calendar/{calendarId}
    Json
    {
        "start": "16/11/2023 10:00",
        "end": "16/11/2023 11:30"
    }
     */
    @GetMapping("calendar/{calendarId}/date/{startStr}/{endStr}")
    public ResponseEntity<?> findAllByCalendarAndDate(@PathVariable Long calendarId, @PathVariable String startStr, @PathVariable String endStr) {
        return ResponseEntity.ok().body(eventService.findAllByCalendarAndStartBetween(calendarId, startStr, endStr));
    }

    @GetMapping("calendar/{calendarId}/today")
    public ResponseEntity<?> findAllByCalendarToday(@PathVariable Long calendarId) {
        return ResponseEntity.ok().body(eventService.findAllByCalendarToday(calendarId));
    }
}
