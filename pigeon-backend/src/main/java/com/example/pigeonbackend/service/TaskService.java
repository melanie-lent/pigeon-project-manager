package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.repo.ProjectRepo;
import com.example.pigeonbackend.repo.TaskRepo;
import com.example.pigeonbackend.repo.TaskSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private TaskSpecifications ts;

    // todo: figure out joining queries

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        taskRepo.findAll().forEach(tasks::add);
        return tasks;
    }

    public Optional<Task> getTask(Integer id) {
        return taskRepo.findById(id);
    }

    public void createTask(Task task) {
        taskRepo.save(task);
    }

    public void updateTask(Integer id, Task task) {
        taskRepo.save(task);
    }

    public void deleteTask(Integer id) {
        taskRepo.deleteById(id);
    }

    // search for tasks in the db that share attributes with the passed-in task
    public List<Task> getTasksByParams(Task task) {
        return ts.search(task);
    }

    public void getAssignees(Integer id) {

    }

    public void getTasksByTags(Integer id) {

    }
}
