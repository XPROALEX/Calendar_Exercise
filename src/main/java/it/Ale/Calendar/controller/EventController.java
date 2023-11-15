package it.Ale.Calendar.controller;

import it.Ale.Calendar.dto.EventDto;
import it.Ale.Calendar.entity.Event;
import it.Ale.Calendar.service.EventService;
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
    public ResponseEntity<?> createEvent(@PathVariable long userId, @PathVariable long calendarId, @RequestBody Event eventInsert) {
        Event event = eventService.create(userId, calendarId, eventInsert);
//        if (event == null) {
//            return ResponseEntity.badRequest().build();
//        }
        return ResponseEntity.ok().body(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (eventService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteByid(id);
        return ResponseEntity.ok().build();
    }
}
