package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Boolean existsByUsername(String username);

    Optional<Object> findByUsername(String username);
}
