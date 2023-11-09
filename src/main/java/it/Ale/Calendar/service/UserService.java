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
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public Iterable<User> findAll(){
        return userRepository.findAll();
    }
    public void save(UserDto userDto){
        User user=new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
    }
    public void deleteByid(int id){
        userRepository.deleteById(id);
    }

}
