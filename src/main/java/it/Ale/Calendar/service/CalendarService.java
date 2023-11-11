package it.Ale.Calendar.service;

import it.Ale.Calendar.entity.Calendar;
import it.Ale.Calendar.entity.User;
import it.Ale.Calendar.repository.CalendarRepository;
import it.Ale.Calendar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalendarService {
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    UserRepository userRepository;

    public Calendar save(long userId, Calendar calendar) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            calendar.setOwner(user);
            return calendarRepository.save(calendar);
        }
        return null;
    }

    public void deleteByid(long id) {
        calendarRepository.deleteById(id);
    }

    public Iterable<Calendar> findAll() {
        return calendarRepository.findAll();
    }

    public Optional<Calendar> findById(long id) {
        return calendarRepository.findById(id);
    }

    public void deleteById(long id) {
        calendarRepository.deleteById(id);
    }

    public Calendar update(long id, Calendar updateCalendar) {
        Calendar existingCalendar = calendarRepository.findById(id).get();
        if (existingCalendar != null) {
            if (updateCalendar.getName() != null) {
                existingCalendar.setName(updateCalendar.getName());
            }
            if (updateCalendar.getDescription() != null) {
                existingCalendar.setDescription(updateCalendar.getDescription());
            }
            return calendarRepository.save(existingCalendar);
        }
        return null;
    }
}
