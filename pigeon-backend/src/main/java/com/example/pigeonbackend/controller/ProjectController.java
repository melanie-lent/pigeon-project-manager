package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService = new ProjectService();

    @GetMapping("/project/all")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
    @RequestMapping(method=RequestMethod.GET, value="/project/{id}")
    public Optional<Project> getProject(@PathVariable Integer id) {
        return projectService.getProject(id);
    }

    @RequestMapping(method= RequestMethod.POST, value="/project")
    public void createProject(@RequestBody Project project) {
        projectService.createProject(project);
    }
    @RequestMapping(method= RequestMethod.PUT, value="/project/{id}")
    public void updateProject(@PathVariable Integer id, @RequestBody Project project) {
        projectService.updateProject(id, project);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/project/{id}")
    public void deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
    }

}
