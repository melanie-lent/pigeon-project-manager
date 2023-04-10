package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {
    @Autowired
    private TaskService taskService = new TaskService();
    @GetMapping("/task/all")
    public Set<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    @RequestMapping(method=RequestMethod.GET, value="/task/{id}")
    public Optional<Task> getTask(@PathVariable UUID id) {
        return taskService.getTask(id);
    }

    @RequestMapping(method= RequestMethod.POST, value="/task")
    public ResponseEntity createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
    @RequestMapping(method= RequestMethod.PUT, value="/task")
    public ResponseEntity updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/task/{id}")
    public ResponseEntity deleteTask(@PathVariable UUID id) {
        return taskService.deleteTask(id);
    }

    @RequestMapping(method=RequestMethod.POST, value="/task/search")
    public List<Task> getTasksByParams(@RequestBody Task task) {
        return taskService.getTasksByParams(task);
    }

}
