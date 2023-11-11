package it.Ale.Calendar.controller;

import it.Ale.Calendar.dto.UserDto;
import it.Ale.Calendar.entity.User;
import it.Ale.Calendar.service.UserService;
import it.Ale.Calendar.dto.ContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
/*
Get all User list
url /user
 */

    @GetMapping
    public Iterable<User> findAll() {
        return userService.findAll();
    }

    /*
    get User By id
    url /user/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userService.findById(id));
    }

    /*
    Get user by email
    url /user/{email}
     */
    @GetMapping("/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        if (userService.findByEmail(email).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.ok().body(userService.findByEmail(email));
    }

    /*
    Delete User
    url /user/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable long id) {
        if (!userService.findById(id).isEmpty()) {
            userService.deleteByid(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    /*
    Register User
    Json
    {
        "name": "name",
        "email": "email",
        "password": "password"}
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService.save(userDto));
    }

    /*
    Edit User
    url /user/{id}
    Json
    {
        "name": "name",
        "email": "email",
        "password": "password"}
    }
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserDto userDto) {
        User editUser = userService.update(id, userDto);
        if (editUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(editUser);
    }

    /*
    Put Contact aggiungere un contatto alla lista
    url /user/{id}/contacts
    Json
    {
        "email": "email"
    }
     */
    @PutMapping("{id}/contacts")
    public ResponseEntity<?> addContact(@PathVariable int id, @RequestBody ContactDto contact) {
        User user = userService.findById(id).get();
        userService.addContact(id, contact);
        return ResponseEntity.ok().body(user.getContacts());
    }
    /*
    Get Contacts
    url /user/{id}/contacts

     */

    @GetMapping("{id}/contacts")
    public ResponseEntity<?> getContacts(@PathVariable int id) {
        User user = userService.findById(id).get();
        return ResponseEntity.ok().body(user.getContacts());
    }
}
