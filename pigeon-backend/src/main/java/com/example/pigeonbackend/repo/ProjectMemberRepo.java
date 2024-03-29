package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectMemberRepo extends JpaRepository<ProjectMember, UUID> {
    public ProjectMember findByMemberIdAndProjectId(UUID memberId, UUID projectId);
    public ProjectMember findByMemberId(UUID memberId);
}
