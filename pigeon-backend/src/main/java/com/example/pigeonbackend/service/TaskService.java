package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.repo.ProjectRepo;
import com.example.pigeonbackend.repo.TaskRepo;
import com.example.pigeonbackend.repo.UserRepo;
import com.example.pigeonbackend.repo.TaskSpecifications;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskSpecifications ts;

    // todo: figure out joining queries

    public Set<Task> getAllTasks() {
        Set<Task> tasks = new HashSet<>();
        taskRepo.findAll().forEach(tasks::add);
        return tasks;
    }

    public Optional<Task> getTask(Integer id) {
        return taskRepo.findById(id);
    }

    public ResponseEntity createTask(Task task) {
        // todo: user needs to be project member
        Integer project_id = task.getProjectId();
        Integer user_id = task.getCreatedBy();
        // first, ensure that the user who is creating the task exists
        if (!userRepo.existsById(user_id)) {
            return new ResponseEntity("Creator user does not exist", HttpStatus.BAD_REQUEST);
        }
        // then, ensure that the project the task is being created under exists
        if (!projectRepo.existsById(project_id)) {
            return new ResponseEntity("Project does not exist", HttpStatus.BAD_REQUEST);
        }
        taskRepo.save(task);
        return new ResponseEntity("Task created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity updateTask(Integer id, Task task) {
        // check that task exists
        if (!taskRepo.existsById(id)) {
            return new ResponseEntity("Task does not exist", HttpStatus.BAD_REQUEST);
        }
        taskRepo.save(task);
        return new ResponseEntity(task, HttpStatus.OK);
    }

    public ResponseEntity deleteTask(Integer id) {
        // check that task exists
        if (!taskRepo.existsById(id)) {
            return new ResponseEntity("Task does not exist", HttpStatus.BAD_REQUEST);
        }
        taskRepo.deleteById(id);
        return new ResponseEntity("Task deleted successfully", HttpStatus.OK);
    }

    // search for tasks in the db that share attributes with the passed-in task
    public List<Task> getTasksByParams(Task task) {
        return ts.search(task);
    }

    public List<User> getAssignees(Task task) {
        return ts.getAssignees(task);
    }

//    public List<Task> getTasksByTags(Integer id) {
//
//    }
}
