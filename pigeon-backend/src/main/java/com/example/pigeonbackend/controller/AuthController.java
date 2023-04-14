package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.JwtUtils;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.datatypes.model.auth.*;
import com.example.pigeonbackend.repo.UserRepo;
import com.example.pigeonbackend.service.AuthenticatedUserService;
import com.example.pigeonbackend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:3000", maxAge = 1000 * 60 * 60)
@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping(method=RequestMethod.POST, value="/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//            System.out.println(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            userDetails.setPassword(null);
            String token = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(userDetails.getId().toString());
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        String jwt = jwtUtils.generateToken(userDetails);
//        String cookieKey = "pigeon-jwt";
//        String cookieValue = loginRequest.getHeader(cookieKey);

//        if (cookieValue == null) {
//            Cookie cookie = new Cookie(cookieKey, jwt);
////            cookie.setMaxAge(30 * 60); // cookie lasts for 30 minutes
////            cookie.setHttpOnly(true); // cookie is not accessible via client-side JavaScript
////            cookie.setSecure(true); // cookie is only sent over HTTPS
//            cookie.setPath("/"); // cookie is accessible on all paths of the domain
////            cookie.setDomain("127.0.0.1"); // cookie is only sent to this domain
//
//            // set the SameSite attribute of the cookie to "None"
////            cookie.setSameSite("None");
//            cookie.setHttpOnly(true);
//            cookie.setSecure(true);
//            cookie.setDomain("127.0.0.1");
//            cookie.setMaxAge(60 * 60);
//
//            response.addCookie(cookie);
//
//        }
    }

    @RequestMapping(method=RequestMethod.POST, value="/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userService.createUser(user)) return new ResponseEntity<>("Created user successfully", HttpStatus.CREATED);
        return new ResponseEntity<>("Couldn't create user. Check parameters, user w/ given email and/or username may already exist", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method=RequestMethod.GET, value="/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
//        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();

        String cookieKey = "pigeon-jwt";
        String cookieValue = request.getHeader(cookieKey);

        Cookie cookie = new Cookie(cookieKey, null);
        cookie.setMaxAge(0); // delete cookie
        response.addCookie(cookie);

        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }

//    @RequestMapping(method=RequestMethod.GET, value="/checkauth")
//    public ResponseEntity<?> checkAuth(HttpServletRequest request) {
////        System.out.println(SecurityContextHolder.getContext());
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        System.out.println(auth);
//
//        if (auth != null) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
}