package it.Ale.Calendar.user;

import it.Ale.Calendar.event.EventDto;
import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.event.Event;
import it.Ale.Calendar.calendar.CalendarService;
import it.Ale.Calendar.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CalendarService calendarService;
    @Autowired
    EventService eventService;
/*
Get all User list
url /user
 */

    @GetMapping
    public Iterable<User> findAll() {
        return userService.findAll();
    }

    /*
    get User By id
    url /user/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        if (userService.findByIdDto(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userService.findByIdDto(id));
    }

    /*
    Delete User
    url /user/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable long id) {
        if (!userService.findById(id).isEmpty()) {
            userService.deleteByid(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    /*
    Register User
    Json
    {
        "name": "name",
        "email": "email",
        "password": "password"}
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.save(user));
    }

    /*
    Edit User
    url /user/{id}
    Json
    {
        "name": "name",
        "email": "email",
        "password": "password"}
    }
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody User user) {
        User editUser = userService.update(id, user);
        if (editUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(editUser);
    }

    /*
    Put Contact aggiungere un contatto alla lista
    url /user/{id}/contacts
    Json
    {
        "email": "email"
    }
     */
    @PutMapping("{id}/contacts")
    public ResponseEntity<?> addContact(@PathVariable int id, @RequestBody ContactDto email) {
        userService.addContact(id, email);
        return ResponseEntity.ok().build();
    }
    /*
    Get Contacts
    url /user/{id}/contacts

     */

    @GetMapping("{id}/contacts")
    public ResponseEntity<?> getContacts(@PathVariable int id) {
        return ResponseEntity.ok().body(userService.getContactsDto(id));
    }

    /*
    Put Edit Calendar
    url /user/{userId}/calendar/{calendarId}
    Json
    {
        "name": "name",
        "description": "description"
    }
     */
    @PutMapping("/{userId}/calendar/{calendarId}")
    public ResponseEntity<?> update(@PathVariable long userId, @PathVariable long calendarId, @RequestBody Calendar calendar) {
        Calendar modifiedCalendar = calendarService.update(userId, calendarId, calendar);
        if (modifiedCalendar == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modifiedCalendar);
    }

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
    "frequency": "DAILY",
    "day": "MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY",
    "count": 5
  }
}
     */
    @PostMapping("/{userId}/calendar/{calendarId}")
    public ResponseEntity<?> createEvent(@PathVariable long userId, @PathVariable long calendarId, @RequestBody EventDto eventDto) {
       eventService.create(userId, calendarId, eventDto);
//        if (event == null) {
//            return ResponseEntity.badRequest().build();
//        }
        return ResponseEntity.ok().body(eventDto);
    }
}
