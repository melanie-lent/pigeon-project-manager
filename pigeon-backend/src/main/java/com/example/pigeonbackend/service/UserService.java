package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@Service
public class UserService {

    // todo add some try catch statements here with status codes
    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public Set<User> getAllUsers() {
        Set<User> users = new HashSet<>();
        userRepo.findAll().forEach(users::add);
        return users;
    }

    public Optional<User> getUser(Integer id) {
        return userRepo.findById(id);
    }

    @ResponseStatus()
    public ResponseEntity createUser(User user) {
//        if (userRepo.existsByUsername(user.getUsername())) {
//            return new ResponseEntity("User already exists", HttpStatus.BAD_REQUEST);
//        }
        user.setHash(bcrypt.encode(user.getHash()));
        userRepo.save(user);
        return new ResponseEntity("Created new user", HttpStatus.CREATED);
    }

    // todo: i worry about this here. can somebody bypass this
    public ResponseEntity updateUser(Integer id, User user) {
        if (!userRepo.existsById(id)) {
            return new ResponseEntity("User does not exist", HttpStatus.BAD_REQUEST);
        }
        if (user.getId() != id) {
            return new ResponseEntity("ID in request body needs to match ID in request parameters", HttpStatus.BAD_REQUEST);
        }
        user.setHash(bcrypt.encode(user.getHash()));
        userRepo.save(user);
        return new ResponseEntity("Updated user", HttpStatus.OK);
    }

    public ResponseEntity deleteUser(Integer id) {
        if (!userRepo.existsById(id)) {
            return new ResponseEntity("User does not exist", HttpStatus.BAD_REQUEST);
        }
        userRepo.deleteById(id);
        return new ResponseEntity("Deleted user", HttpStatus.OK);
    }

    public Set<Project> getProjects(Integer user_id) {
        Optional<User> user = userRepo.findById(user_id);
//        return user.get().getMembership();
        return null;
    }
}
