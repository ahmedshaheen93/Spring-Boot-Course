package com.shaheen.demoapi.controller;

import com.shaheen.demoapi.exception.BadRequestExpection;
import com.shaheen.demoapi.exception.NotFoundException;
import com.shaheen.demoapi.model.Role;
import com.shaheen.demoapi.model.User;
import com.shaheen.demoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.findAll();
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@Valid @PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new NotFoundException(String.format("can't find user with id (%s) on our db", id));
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        user = userService.save(user,new Role("ROLE_USER"));
        if (user.getId() > 0) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            throw new BadRequestExpection("user Already exists");
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        if (user.getId() != null && user.getId() > 0) {
            user = userService.update(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new BadRequestExpection(String.format("can't find user with id (%s) on our db", user.getId()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity updateUser(@Valid @PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user != null) {
            userService.delete(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new NotFoundException("user not founded");
        }
    }

}
