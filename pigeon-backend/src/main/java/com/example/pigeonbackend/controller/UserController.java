package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService = new UserService();

    // TODO: 2/14/2023 comment out "getall" type functions for production

    @GetMapping("/user/all")
    public Set<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @RequestMapping(method=RequestMethod.GET, value="/user")
    public Optional<User> getUser(@RequestBody UUID id) {
        return userService.getUser(id);
    }
    @RequestMapping(method= RequestMethod.PUT, value="/user")
    public ResponseEntity updateUser(@RequestBody UUID id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/user")
    public ResponseEntity deleteUser(@RequestBody UUID id) {
        return userService.deleteUser(id);
    }

    @RequestMapping(method = RequestMethod.GET, value="/user/projects")
    public Set<Project> getProjects(@RequestBody UUID id) {
        return userService.getProjects(id);
    }

}
