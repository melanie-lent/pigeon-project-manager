package com.example.pigeonbackend.datatypes.model.auth;

import com.example.pigeonbackend.datatypes.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class RefreshToken {
    @Id
    private UUID id;
    @OneToOne
    private User user;

}
