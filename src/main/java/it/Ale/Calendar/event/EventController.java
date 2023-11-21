package it.Ale.Calendar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;

    /*
    Post Create Event
    url /event/{userId}/{calendarName}
    Json
    {
        "name": "name",
        "description": "description",
        "start": "dd/MM/yyyy HH:mm",
        "end": "dd/MM/yyyy HH:mm",
        "recurrence": "recurrence",
        "users": [
            {
                "id": 1
            },  {
                "id": 2
            }
        ]

    }

     */
    @PostMapping("/{userId}/{calendarId}")
    public ResponseEntity<?> createEvent(@PathVariable long userId, @PathVariable long calendarId, @RequestBody EventDto eventDto) {
         eventService.create(userId, calendarId, eventDto);
//        if (event == null) {
//            return ResponseEntity.badRequest().build();
//        }
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
}
