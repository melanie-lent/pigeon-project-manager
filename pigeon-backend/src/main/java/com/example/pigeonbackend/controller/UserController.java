package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000", maxAge = 1000 * 60 * 60)
//@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService = new UserService();

    // TODO: 2/14/2023 comment out "getall" type functions for production

//    @GetMapping("/user/all")
//    public Set<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
    @RequestMapping(method=RequestMethod.GET, value="/user/{id}")
    public Optional<User> getUser(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return userService.getUser(id, authToken);
    }
    @RequestMapping(method= RequestMethod.PUT, value="/user")
    public ResponseEntity updateUser(@RequestBody UUID id, @RequestBody User user, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return userService.updateUser(id, user, authToken);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/user")
    public ResponseEntity deleteUser(@RequestBody UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return userService.deleteUser(id, authToken);
    }

    @RequestMapping(method = RequestMethod.GET, value="/user/ownedprojects/{id}")
    public Set<Project> getProjectsByOwner(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return userService.getProjectsByOwner(id, authToken);
    }

    @RequestMapping(method = RequestMethod.GET, value="/user/memberprojects/{id}")
    public Set<Project> getProjectsByMemberNotOwner(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return userService.getProjectsByMemberNotOwner(id, authToken);
    }
    @RequestMapping(method = RequestMethod.GET, value="/user/assignedtasks/{id}")
    public Set<Task> getTasksByUser(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return userService.getTasksByUser(id, authToken);
    }

}
