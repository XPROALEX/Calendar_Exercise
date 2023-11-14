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
        "start": "dd/MM/yyyy",
        "end": "dd/MM/yyyy",
        "partecipantsId": [1,2,3]
    }
     */
    @PostMapping("/{userId}/{calendarName}")
    public ResponseEntity<?> createEvent(@PathVariable long userId, @PathVariable String calendarName, @RequestBody EventDto eventDto) {
        Event event = eventService.save(userId, calendarName, eventDto);
        if (event == null) {
            return ResponseEntity.badRequest().build();
        }
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
}
