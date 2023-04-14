package com.example.pigeonbackend.service;

import com.example.pigeonbackend.JwtUtils;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.repo.ProjectRepo;
import com.example.pigeonbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class AuthenticatedUserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private JwtUtils jwtUtils;

    public boolean idMatches(UUID id, String token){
        String tokenId =  jwtUtils.getIdFromToken(token).toString();
//        User user = (User) userRepo.findByUsername(username).get();
        return id.toString().equals(tokenId);
    }
}
