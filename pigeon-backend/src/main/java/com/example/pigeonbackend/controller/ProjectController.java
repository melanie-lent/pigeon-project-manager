package com.example.pigeonbackend.controller;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.ProjectMember;
import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class ProjectController {
    @Autowired
    private ProjectService projectService = new ProjectService();

//    @GetMapping("/project/all")
//    public List<Project> getAllProjects() {
//        return projectService.getAllProjects();
//    }
    @RequestMapping(method=RequestMethod.GET, value="/project/{id}")
    public Optional<Project> getProject(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.getProject(id, authToken);
    }
    @RequestMapping(method=RequestMethod.GET,value="/project/tasks/{id}")
    public Set<Task> getTasksByProject(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.getTasksByProject(id, authToken);
    }
    @RequestMapping(method= RequestMethod.POST, value="/project")
    public ResponseEntity createProject(@RequestBody Project project, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.createProject(project, authToken);
    }
    @RequestMapping(method= RequestMethod.PUT, value="/project")
    public ResponseEntity updateProject(@RequestBody Project project, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.updateProject(project, authToken);
    }
    @RequestMapping(method=RequestMethod.PUT, value="/project/addmember")
    public ResponseEntity addMember(@RequestParam UUID projectId, @RequestParam UUID memberId, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.addMember(projectId, memberId, authToken);
    }
    @RequestMapping(method=RequestMethod.PUT, value="/project/removemember")
    public ResponseEntity removeMember(@RequestParam UUID projectId, @RequestParam UUID memberId, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.removeMember(projectId, memberId, authToken);
    }
    @RequestMapping(method=RequestMethod.PUT, value="/project/editperms")
    public ResponseEntity editPerms(@RequestParam UUID projectId, @RequestBody ProjectMember projectMember, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.editPerms(projectId, projectMember, authToken);
    }
    @RequestMapping(method= RequestMethod.DELETE, value="/project/{id}")
    public ResponseEntity deleteProject(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.deleteProject(id, authToken);
    }
    @RequestMapping(method = RequestMethod.GET, value="/project/members/{id}")
    public Set<User> getMembers(@PathVariable UUID id, HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return projectService.getMembers(id, authToken);
    }
}
