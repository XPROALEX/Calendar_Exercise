package it.Ale.Calendar.service;

import it.Ale.Calendar.dto.ContactDto;
import it.Ale.Calendar.dto.UserDto;
import it.Ale.Calendar.entity.User;
import it.Ale.Calendar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public User save(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return userRepository.save(user);
    }

    public void deleteByid(long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public User update(long id, UserDto userDto) {
        User existingUser = userRepository.findById(id).get();
        if (existingUser == null) {
            return null;
        }
        if (existingUser != null) {
            User user = userRepository.findById(id).get();
            if (userDto.getName() != null) {
                user.setName(userDto.getName());
            }
            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
            }
            if (userDto.getPassword() != null) {
                user.setPassword(userDto.getPassword());
            }
            return userRepository.save(user);
        }
        return null;
    }

    public void addContact(long id, ContactDto contactDto) {
        User user = userRepository.findById(id).get();
        User contact = userRepository.findByEmail(contactDto.getEmail()).get();
        if (contact != null) {
            user.getContacts().add(contact);
            userRepository.save(user);
        }
    }

    public Iterable<User> getContacts(long id) {
        User user = userRepository.findById(id).get();
        return user.getContacts();
    }
}
