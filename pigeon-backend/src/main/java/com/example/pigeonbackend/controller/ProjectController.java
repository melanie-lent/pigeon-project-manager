package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class ProjectController {
    @Autowired
    private ProjectService projectService = new ProjectService();

    @GetMapping("/project/all")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
    @RequestMapping(method=RequestMethod.GET, value="/project/{id}")
    public Optional<Project> getProject(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.getProject(id, authToken);
    }
    @RequestMapping(method= RequestMethod.POST, value="/project")
    public ResponseEntity createProject(@RequestBody Project project, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.createProject(project, authToken);
    }
    @RequestMapping(method= RequestMethod.PUT, value="/project")
    public ResponseEntity updateProject(@RequestBody Project project) {
        return projectService.updateProject(project);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/project/{id}")
    public ResponseEntity deleteProject(@PathVariable UUID id) {
        return projectService.deleteProject(id);
    }

    @RequestMapping(method = RequestMethod.GET, value="/project/members/{id}")
    public Set<User> getMembers(@PathVariable UUID id) {
        return projectService.getMembers(id);
    }

}
