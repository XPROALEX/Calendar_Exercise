package it.Ale.Calendar.calendar;

import it.Ale.Calendar.calendar.Calendar;
import it.Ale.Calendar.user.User;
import it.Ale.Calendar.calendar.CalendarRepository;
import it.Ale.Calendar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalendarService {
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    UserRepository userRepository;

    public Calendar create(long userId, Calendar calendar) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            calendar.setOwner(user);
            user.getCalendars().add(calendar);
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


    public Calendar update(long userId, long id, Calendar updateCalendar) {
        Optional<User> userOptional = userRepository.findById(userId);
        Calendar existingCalendar = calendarRepository.findById(id).get();
        if (userOptional.isPresent()) {
            if (existingCalendar != null && existingCalendar.getOwner().getId() == userId) {
                if (updateCalendar.getName() != null) {
                    existingCalendar.setName(updateCalendar.getName());
                }
                if (updateCalendar.getDescription() != null) {
                    existingCalendar.setDescription(updateCalendar.getDescription());
                }
                return calendarRepository.save(existingCalendar);
            }
        }
        return null;
    }
}
