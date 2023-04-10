package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.datatypes.model.auth.UserDetailsImpl;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    // todo add some try catch statements here with status codes
    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    private JwtService jwtService;

    @Autowired
    private AuthHelper authHelper;

    // todo: next step is to do actual authentication on /login and /signup
//    @ResponseStatus()
//    public AuthResponse signup(User user) {
//        try {
//            createUser(user);
//            var jwtToken = jwtService.generateToken((UserDetails) user); // todo: this almost definitely doesnt cast properly
//            return AuthResponse.builder()
//                    .token(jwtToken)
//                    .build();
//        } catch (Exception NoSuchElementException) {
//            return
//        }
//    }
//
//    @ResponseStatus()
//    public AuthResponse login(AuthRequest req) {
//        authManager.authenticate(new UsernamePasswordAuthenticationToken((req.getUsername(), req.getHash())))
//    }

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

    @PreAuthorize("id == authHelper.getPrincipalId()")
    public Optional<User> getUser(UUID id) {
        return userRepo.findById(id);
    }

    // todo: i worry about this here. can somebody bypass this
    @PreAuthorize("id == authHelper.getPrincipalId()")
    public ResponseEntity updateUser(UUID id, User user) {
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

    @PreAuthorize("id == authHelper.getPrincipalId()")
    public ResponseEntity deleteUser(UUID id) {
        if (!userRepo.existsById(id)) {
            return new ResponseEntity("User does not exist", HttpStatus.BAD_REQUEST);
        }
        userRepo.deleteById(id);
        return new ResponseEntity("Deleted user", HttpStatus.OK);
    }

    @PreAuthorize("id == authHelper.getPrincipalId()")
    public Set<Project> getProjects(UUID id) {
        Optional<User> user = userRepo.findById(id);
//        return user.get().getMembership();
        return null;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
