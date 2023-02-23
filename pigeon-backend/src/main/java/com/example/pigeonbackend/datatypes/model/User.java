package com.example.pigeonbackend.datatypes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@DynamicUpdate
@Table(name="users", schema="user_data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String hash;

//    private String email;
    private String username;
    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column(name="created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp created_on;
    @Column
    private Timestamp last_login;

    public User(String username, String hash, String first_name, String last_name) {
        super();
        System.out.println(String.format("hellohello", hash,username,first_name,last_name));
        this.hash=hash;
//        this.email=email;
        this.username=username;
        this.first_name=first_name;
        this.last_name=last_name;
        this.created_on=(Timestamp) Date.from(Instant.now());
    }

    // TODO: 2/14/2023 Create sanitization for the set functions

    public void setHash(String hash) {
        this.hash=hash;
    }

    public void setUsername(String username) { this.username=username; }

//    public void setEmail(String hash) {
//        this.email=email;
//    }

    public void setFirstName(String first_name) {
        this.first_name=first_name;
    }

    public void setLastName(String last_name) {
        this.last_name=last_name;
    }

    public void setLastLogin() {
        this.last_login=(Timestamp) Date.from(Instant.now());
    }
}
