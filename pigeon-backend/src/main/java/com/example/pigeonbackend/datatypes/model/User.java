package com.example.pigeonbackend.datatypes;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="user")
public class User {
    @Id
    private long id;
    private String hash;
    private String email;

    private String first_name;
    private String last_name;
    private Timestamp created_on;
    private Timestamp last_login;
}
