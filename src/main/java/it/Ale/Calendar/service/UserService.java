package it.Ale.Calendar.service;

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

    public void deleteByid(int id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public User update(int id, UserDto userDto) {
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

}
