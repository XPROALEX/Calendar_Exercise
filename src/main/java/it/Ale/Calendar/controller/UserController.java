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
    public ResponseEntity<?> findById(@PathVariable long id) {
        if (userService.findByIdDto(id)==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userService.findByIdDto(id));
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
    public ResponseEntity<?> save(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.save(user));
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
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody User user) {
        User editUser = userService.update(id, user);
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
    public ResponseEntity<?> addContact(@PathVariable int id, @RequestBody String email) {
        userService.addContact(id,email);
        return ResponseEntity.ok().build();
    }
    /*
    Get Contacts
    url /user/{id}/contacts

     */

    @GetMapping("{id}/contacts")
    public ResponseEntity<?> getContacts(@PathVariable int id) {
        return ResponseEntity.ok().body(userService.getContactsDto(id));
    }
}
