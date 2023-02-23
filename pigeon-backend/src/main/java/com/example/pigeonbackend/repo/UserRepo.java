package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    boolean existsByUsername(String username);
}
