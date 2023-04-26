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
     @Query(value = "SELECT * FROM project_data.projects projects JOIN project_data.project_members project_members ON (projects.id=project_members.project_id) WHERE (owner_id != ?1 AND ?1 IN (select project_data.project_members.member_id from project_data.project_members))", nativeQuery = true)
     Set<Project> getProjectsByMemberNotOwner(UUID id);
}
