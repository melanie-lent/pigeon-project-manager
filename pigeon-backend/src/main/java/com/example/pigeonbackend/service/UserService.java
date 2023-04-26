package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.datatypes.model.auth.UserDetailsImpl;
import com.example.pigeonbackend.repo.ProjectRepo;
import com.example.pigeonbackend.repo.TaskRepo;
import com.example.pigeonbackend.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    // todo add some try catch statements here with status codes
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskRepo taskRepo;
    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
//    private JwtService jwtService;

    @Autowired
    private AuthenticatedUserService authHelper;
    @Autowired
    private ProjectRepo projectRepo;

    public Boolean createUser(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            return false;
        }
        user.setPassword(bcrypt.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }

    public Set<User> getAllUsers() {
        Set<User> users = new HashSet<>();
        userRepo.findAll().forEach(users::add);
        return users;
    }

    @PreAuthorize("@authHelper.idMatches(#id, #authToken)")
    public Optional<User> getUser(UUID id, String authToken) {
        return userRepo.findById(id);
    }

    @PreAuthorize("@authHelper.idMatches(#id, #authToken)")
    public ResponseEntity updateUser(UUID id, User user, String authToken) {
        if (!userRepo.existsById(id)) {
            return new ResponseEntity("User does not exist", HttpStatus.BAD_REQUEST);
        }
        if (user.getId() != id) {
            return new ResponseEntity("ID in request body needs to match ID in request parameters", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(bcrypt.encode(user.getPassword()));
        userRepo.save(user);
        return new ResponseEntity("Updated user", HttpStatus.OK);
    }

    @PreAuthorize("@authHelper.idMatches(#id, #authToken)")
    public ResponseEntity deleteUser(UUID id, String authToken) {
        try {
            userRepo.deleteById(id);
            return new ResponseEntity("Deleted user", HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity("User does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("@authHelper.idMatches(#id, #authToken)")
    public Set<Project> getProjectsByOwner(UUID id, String authToken) {
        try {
            return projectRepo.findAllByOwnerId(id);
        } catch (Exception NoSuchElementException) {
            return new HashSet<>();
        }
    }

    @PreAuthorize("@authHelper.idMatches(#id, #authToken)")
    public Set<Project> getProjectsByMemberNotOwner(UUID id, String authToken) {
        try {
            return projectRepo.getProjectsByMemberNotOwner(id);
        } catch (Exception NoSuchElementException) {
            return new HashSet<>();
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    @PreAuthorize("@authHelper.idMatches(#id, #authToken)")
    public Set<Task> getTasksByUser(UUID id, String authToken) {
        return taskRepo.findAllByAssignees(id);
    }
}
