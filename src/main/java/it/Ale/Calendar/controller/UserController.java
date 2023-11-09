package it.Ale.Calendar.controller;

import it.Ale.Calendar.dto.UserDto;
import it.Ale.Calendar.entity.User;
import it.Ale.Calendar.service.UserService;
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
 */

    @GetMapping
    public Iterable<User> findAll() {
        return userService.findAll();
    }

    /*
    get User By id
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
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable int id) {
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
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody UserDto userDto) {
        User editUser = userService.update(id, userDto);
        if (editUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(editUser);
    }
}
