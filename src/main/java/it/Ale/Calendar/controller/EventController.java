package it.Ale.Calendar.controller;

import it.Ale.Calendar.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    @Autowired
    EventService eventService;
}
