package com.example.pigeonbackend.datatypes.model.auth;

import java.util.UUID;

public class JwtResponse {
    private String jwt;
    private UUID id;
    private String username;
//    private String email;
    private String password;
    public JwtResponse(String jwt, UUID id, String username) {
        this.jwt = jwt;
        this.id = id;
        this.username = username;
//        this.username = username;
    }
    public String getUsername() {
        return username;
    }
//    public String getPassword() {
//        return password;
//    }
    public UUID getId() {
        return id;
    }
    public String getJwt() {
        return jwt;
    }
//    public String getEmail() {
//        return email;
//    }
}
