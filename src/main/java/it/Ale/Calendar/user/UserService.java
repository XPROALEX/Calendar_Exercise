package it.Ale.Calendar.user;

import it.Ale.Calendar.calendar.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        Calendar calendar = new Calendar();
        calendar.setName("privato");
        calendar.setDescription("primo calendario utente");
        user.getCalendars().add(calendar);
        calendar.setOwner(user);
        return userRepository.save(user);
    }

    public void deleteByid(long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public UserDto findByIdDto(long id) {
        UserDto userDto = new UserDto();
        User user = userRepository.findById(id).get();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setCalendars(user.getCalendars());
        userDto.setEvents(user.getEvents());
        user.getContacts().forEach(x -> {
            ContactDto contactDto = new ContactDto();
            contactDto.setEmail(x.getEmail());
            contactDto.setName(x.getName());
            userDto.getContacts().add(contactDto);
        });
        return userDto;

    }


    public User update(long id, User user) {
        User existingUser = userRepository.findById(id).get();
        if (existingUser == null) {
            return null;
        }
        if (existingUser != null) {
            User editUser = userRepository.findById(id).get();
            if (user.getName() != null) {
                editUser.setName(user.getName());
            }
            if (user.getEmail() != null) {
                editUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                editUser.setPassword(user.getPassword());
            }
            return userRepository.save(editUser);
        }
        return null;
    }

    public void addContact(long id, ContactDto contactDto) {
        User user = userRepository.findById(id).get();
        User contact = userRepository.findByEmail(contactDto.getEmail()).get();
        if (!user.getContacts().contains(contact) && contact != null) {
            user.getContacts().add(contact);
            userRepository.save(user);
        } else throw new RuntimeException("Contact already exists");
    }
    public Iterable<ContactDto> getContactsDto(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            Set<ContactDto> contactsDto = new HashSet<>();
            user.get().getContacts().forEach(x -> {
                ContactDto contactDto = new ContactDto();
                contactDto.setEmail(x.getEmail());
                contactDto.setName(x.getName());
                contactsDto.add(contactDto);
            });
            return contactsDto;
        } else return Collections.emptySet();
    }
}
