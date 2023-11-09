package it.Ale.Calendar.service;

import it.Ale.Calendar.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {
    @Autowired
    CalendarRepository calendarRepository;
}
