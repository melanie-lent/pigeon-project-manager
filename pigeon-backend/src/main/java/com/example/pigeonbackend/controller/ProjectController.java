package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService = new ProjectService();

    @GetMapping("/project/all")
    public Set<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
    @RequestMapping(method=RequestMethod.GET, value="/project/{id}")
    public Optional<Project> getProject(@PathVariable Integer id) {
        return projectService.getProject(id);
    }

    @RequestMapping(method= RequestMethod.POST, value="/project")
    public ResponseEntity createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }
    @RequestMapping(method= RequestMethod.PUT, value="/project/{project_id}")
    public ResponseEntity updateProject(@PathVariable Integer project_id, @RequestBody Project project) {
        return projectService.updateProject(project_id, project);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/project/{id}")
    public ResponseEntity deleteProject(@PathVariable Integer id) {
        return projectService.deleteProject(id);
    }

    @RequestMapping(method = RequestMethod.GET, value="/project/members/{id}")
    public Set<User> getMembers(@PathVariable Integer id) {
        return projectService.getMembers(id);
    }

}
