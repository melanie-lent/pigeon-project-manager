package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepo extends CrudRepository<Project, Integer> {
}
