package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService = new TaskService();
    @GetMapping("/task/all")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    @RequestMapping(method=RequestMethod.GET, value="/task/{id}")
    public Optional<Task> getTask(@PathVariable Integer id) {
        return taskService.getTask(id);
    }

    @RequestMapping(method= RequestMethod.POST, value="/task")
    public void createTask(@RequestBody Task task) {
        taskService.createTask(task);
    }
    @RequestMapping(method= RequestMethod.PUT, value="/task/{id}")
    public void updateTask(@PathVariable Integer id, @RequestBody Task task) {
        taskService.updateTask(id, task);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/task/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }

    @RequestMapping(method=RequestMethod.POST, value="/task/search")
    public List<Task> getTasksByParams(@RequestBody Task task) {
        return taskService.getTasksByParams(task);
    }

}
