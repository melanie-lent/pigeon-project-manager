package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepo extends JpaRepository<ProjectMember, Integer> {
    public List<ProjectMember> findByProjectIdAndMemberId(Integer projectId, Integer memberId);
}
