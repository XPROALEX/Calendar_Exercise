package it.Ale.Calendar.controller;

import it.Ale.Calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {
    @Autowired
    CalendarService calendarService;
}
