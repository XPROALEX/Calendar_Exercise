package it.Ale.Calendar.controller;

import it.Ale.Calendar.entity.Calendar;
import it.Ale.Calendar.service.CalendarService;
import it.Ale.Calendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    CalendarService calendarService;
    @Autowired
    UserService userService;

    /*
    Get allCalendari
    url /calendar
     */
    @GetMapping
    public Iterable<Calendar> findAll() {
        return calendarService.findAll();
    }

    /*
    Get calendario specifico
    url /calendar/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (calendarService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(calendarService.findById(id));
    }

    /*
    create Calendar
    url /calendar/{userId}
    Json
    {
        "name": "name",
        "description": "description",
     */
    @PostMapping("/{userId}")
    public ResponseEntity<?> create(@PathVariable long userId, @RequestBody Calendar calendar) {
        if (userService.findById(userId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(calendarService.save(userId, calendar));
    }

    /*
    Put Edit Calendar
    url /calendar/{userId}/{id}
    Json
    {
        "name": "name",
        "description": "description"
    }
     */
    @PutMapping("/{userId}/{id}")
    public ResponseEntity<?> update(@PathVariable long userId, @PathVariable Long id, @RequestBody Calendar calendar) {
        Calendar modifiedCalendar = calendarService.update(userId, id, calendar);
        if (modifiedCalendar == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modifiedCalendar);
    }

}
