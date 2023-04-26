package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000", maxAge = 1000 * 60 * 60)
public class TaskController {
    @Autowired
    private TaskService taskService = new TaskService();
//    @GetMapping("/task/all")
//    public Set<Task> getAllTasks() {
//        return taskService.getAllTasks();
//    }
    @RequestMapping(method=RequestMethod.GET, value="/task/{id}")
    public Optional<Task> getTask(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return taskService.getTask(id, authToken);
    }
//    @RequestMapping(method=RequestMethod.GET, value="/user/task/{id}")
//    public Set<Task> getTasksByUser(@PathVariable UUID id, HttpServletRequest request) {
//        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//        return taskService.getTasksByUser(id, authToken);
//    }
    @RequestMapping(method= RequestMethod.POST, value="/task")
    public ResponseEntity createTask(@RequestBody Task task, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return taskService.createTask(task, authToken);
    }
    @RequestMapping(method= RequestMethod.PUT, value="/task")
    public ResponseEntity updateTask(@RequestBody Task task, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return taskService.updateTask(task, authToken);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/task/{id}")
    public ResponseEntity deleteTask(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return taskService.deleteTask(id, authToken);
    }

    @RequestMapping(method=RequestMethod.POST, value="/task/search/{projectId}")
    public List<Task> getTasksByParams(@PathVariable UUID projectId, @RequestBody Task task, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return taskService.getTasksByParams(projectId, task, authToken);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/task/addassignee")
    public ResponseEntity<Object> addAssignee(@RequestParam UUID taskId, @RequestParam UUID userId, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return taskService.addAssignee(taskId, userId, authToken);
    }
    @RequestMapping(method=RequestMethod.PUT, value="/task/removeassignee")
    public ResponseEntity<Object> removeAssignee(@RequestParam UUID taskId, @RequestParam UUID userId, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return taskService.removeAssignee(taskId, userId, authToken);
    }
}
