package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepo extends JpaRepository<Project, UUID> {
//     Boolean existsByProjectNameAndOwnerId(String project_name, Integer owner_id);
//
//     List<Project> findByProjectNameAndOwnerId(String projectName, Integer ownerId);
     List<Project> findByNameContainingIgnoreCase(String name);
}
