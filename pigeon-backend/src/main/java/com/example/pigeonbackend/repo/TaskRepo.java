package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

//    Optional<Task> findByProject_idAndCreated_by(Integer project_id, Integer created_by);
//    Optional<Task> findByProject_id(Integer project_id);
    List<Task> findByTaskNameContaining(String taskName);
}
