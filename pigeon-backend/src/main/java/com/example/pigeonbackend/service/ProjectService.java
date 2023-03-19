package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.ProjectMember;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.repo.ProjectMemberRepo;
import com.example.pigeonbackend.repo.ProjectRepo;
import com.example.pigeonbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.isNull;

@Service
public class ProjectService {

    // todo: users must exist for a project with a user as its creator in order to exist. deletions cascade.
    //todo: see if you can make the combination of project name and owner id have to be unique. ask russ
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectMemberRepo projectMemberRepo;

    public Set<Project> getAllProjects() {
        Set<Project> projects = new HashSet<>();
        projectRepo.findAll().forEach(projects::add);
        return projects;
    }

    public Optional<Project> getProject(Integer id) {
        return projectRepo.findById(id);
    }

    public ResponseEntity createProject(Project project) {
        // first, check that the user who owns the project exists
        Optional<User> optOwner = userRepo.findById(project.getOwnerId());
        User owner;
        try {
            owner = optOwner.get();
            // then, check that there isn't already a project by that owner with the same name

            // actually fuck it users can have multiple "KickassCorp" projects
//        System.out.println(project);
////        if (!projectRepo.findByProjectNameAndOwnerId(project.getProjectName(), project.getOwnerId()).isEmpty()) {
////            return new ResponseEntity("Project by that owner already exists", HttpStatus.BAD_REQUEST);
////        }
//        project.addToMembers(owner);
//
            owner.addToInProjects(project);
            project.addToMembers(owner);

            projectRepo.save(project);

            ProjectMember projectMember = projectMemberRepo.findByProjectIdAndMemberId(project.getId(), owner.getId()).get(0);
            projectMember.setCanAssignTask(true);
            projectMember.setCanCreateTask(true);
            projectMember.setCanDeleteTask(true);
            projectMember.setCanEditTask(true);
            projectMember.setCanSetDueDate(true);

            projectMemberRepo.save(projectMember);

            return new ResponseEntity("Project created successfully", HttpStatus.CREATED);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity("User does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity updateProject(Integer id, Project project) {
        // check that project exists
        if (!projectRepo.existsById(id)) {
            return new ResponseEntity("Project does not exist", HttpStatus.BAD_REQUEST);
        }
        projectRepo.save(project);
        return new ResponseEntity(project, HttpStatus.OK);
    }

    public ResponseEntity deleteProject(Integer id) {
        // check that project exists
        if (!projectRepo.existsById(id)) {
            return new ResponseEntity("Project does not exist", HttpStatus.BAD_REQUEST);
        }
        projectRepo.deleteById(id);
        return new ResponseEntity("Project deleted successfully", HttpStatus.OK);
    }

    public Set<User> getMembers(Integer project_id) {
        Optional<Project> project = projectRepo.findById(project_id);
        return project.map(Project::getMembers).orElse(null);
    }
}
