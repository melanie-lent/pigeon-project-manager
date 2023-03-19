package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    private UserService userService = new UserService();

    // TODO: 2/14/2023 comment out "getall" type functions for production

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("hello world, I have just started up");
    }

    @GetMapping("/user/all")
    public Set<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @RequestMapping(method=RequestMethod.GET, value="/user/{id}")
    public Optional<User> getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @RequestMapping(method= RequestMethod.POST, value="/user")
    public ResponseEntity createUser(@RequestBody User user) {
        System.out.println(user);
        return userService.createUser(user);

    }
    @RequestMapping(method= RequestMethod.PUT, value="/user/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/user/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @RequestMapping(method = RequestMethod.GET, value="/user/projects/{id}")
    public Set<Project> getProjects(@PathVariable Integer id) {
        return userService.getProjects(id);
    }

}
