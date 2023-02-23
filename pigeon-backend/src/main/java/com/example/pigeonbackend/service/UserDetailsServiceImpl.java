package com.example.pigeonbackend.service;

import com.example.pigeonbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public abstract class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

}