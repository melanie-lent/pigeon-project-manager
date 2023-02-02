package com.example.pigeonbackend.datatypes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="user")
public class User {
    @Id
    private Long id;

    private String hash;
    private String email;

    private String first_name;
    private String last_name;
    private Timestamp created_on;
    private Timestamp last_login;
}
