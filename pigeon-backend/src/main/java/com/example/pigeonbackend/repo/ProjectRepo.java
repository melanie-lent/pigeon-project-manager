package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProjectRepo extends JpaRepository<Project, UUID> {
//     Boolean existsByProjectNameAndOwnerId(String project_name, Integer owner_id);
//
//     List<Project> findByProjectNameAndOwnerId(String projectName, Integer ownerId);
     Set<Project> findByNameContainingIgnoreCase(String name);
     Set<Project> findAllByOwnerId(UUID uuid);
     List<Project> findAll();
     @Query(value = "SELECT * FROM PROJECTS UNION PROJECT_MEMBERS ON PROJECT.ID=PROJECT_MEMBERS.PROJECT_ID WHERE OWNER_ID != ?1", nativeQuery = true)
     Set<Project> getProjectsByMemberNotOwner(UUID id);
}
