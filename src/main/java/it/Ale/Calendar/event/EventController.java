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
    /*
    GET Events By Calendar
    url /calendar/{calendarId}
     */

    @GetMapping("calendar/{calendarId}")
    public ResponseEntity<?> findAllByCalendar(@PathVariable Long calendarId) {
        return ResponseEntity.ok().body(eventService.findAllByCalendar(calendarId));
    }

    /*
    get Events By Calendar between two date
    url /calendar/{calendarId}/date/{startStr}/{endStr}
    es. /calendar/1/date/2023-11-16/2023-11-17
    forse da cambiare con @RequestParam
     */
    @GetMapping("calendar/{calendarId}/date/{startStr}/{endStr}")
    public ResponseEntity<?> findAllByCalendarAndDate(@PathVariable Long calendarId, @PathVariable String startStr, @PathVariable String endStr) {
        return ResponseEntity.ok().body(eventService.findAllByCalendarBetweenTwoDate(calendarId, startStr, endStr));
    }

    /*
    GET Events By Calendar today
    url /calendar/{calendarId}/today
     */
    @GetMapping("calendar/{calendarId}/today")
    public ResponseEntity<?> findAllByCalendarToday(@PathVariable Long calendarId) {
        return ResponseEntity.ok().body(eventService.findAllByCalendarToday(calendarId));
    }

    /*
    GET Events By Calendar this week
    url /calendar/{calendarId}/week
     */
    @GetMapping("calendar/{calendarId}/week")
    public ResponseEntity<?> findAllByCalendarInThisWeek(@PathVariable Long calendarId) {
        return ResponseEntity.ok().body(eventService.findAllByCalendarInThiWeek(calendarId));
    }

    /*
    GET Events By Calendar this month
    url /calendar/{calendarId}/month
     */
    @GetMapping("calendar/{calendarId}/month")
    public ResponseEntity<?> findAllByCalendarInThisMonth(@PathVariable Long calendarId) {
        return ResponseEntity.ok().body(eventService.findAllByCalendarInThisMonth(calendarId));
    }

    /*
    GET Events By Participants
    url /participant/{ParticipantId}
     */
    @GetMapping("participant/{ParticipantId}")
    public ResponseEntity<?> findAllByParticipantsId(@PathVariable Long ParticipantId) {
        return ResponseEntity.ok().body(eventService.findAllByParticipantId(ParticipantId));
    }

    /*
    GET Events By Participant between two date
    url /participant/{ParticipantId}/date/{startStr}/{endStr}
    es. /participant/1/date/2023-11-16/2023-11-17
     */
    @GetMapping("participant/{ParticipantId}/date/{startStr}/{endStr}")
    public ResponseEntity<?> findAllByParticipantIdAndDate(@PathVariable Long ParticipantId, @PathVariable String startStr, @PathVariable String endStr) {
        return ResponseEntity.ok().body(eventService.findAllByParticipantIdBetweenTwoDate(ParticipantId, startStr, endStr));
    }
    /*
    GET Events By Participant today
    url /participant/{ParticipantId}/today
     */

    @GetMapping("participant/{ParticipantId}/today")
    public ResponseEntity<?> findAllByParticipantIdToday(@PathVariable Long ParticipantId) {
        return ResponseEntity.ok().body(eventService.findAllByParticipantIdToday(ParticipantId));
    }

    /*
    GET Events By Participant this week
    url /participant/{ParticipantId}/week
     */
    @GetMapping("participant/{ParticipantId}/week")
    public ResponseEntity<?> findAllByParticipantIdInThisWeek(@PathVariable Long ParticipantId) {
        return ResponseEntity.ok().body(eventService.findAllByParticipantIdInThiWeek(ParticipantId));
    }

    /*
    GET Events By Participant this month
    url /participant/{ParticipantId}/month
     */
    @GetMapping("participant/{ParticipantId}/month")
    public ResponseEntity<?> findAllByParticipantIdInThisMonth(@PathVariable Long ParticipantId) {
        return ResponseEntity.ok().body(eventService.findAllByParticipantIdInThisMonth(ParticipantId));
    }


}
