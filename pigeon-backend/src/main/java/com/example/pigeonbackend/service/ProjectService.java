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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private AuthenticatedUserService authHelper;

    public List<Project> getAllProjects() {
        System.out.println("get all projects");
        List<Project> projects = projectRepo.findAll();
        return projects;
//        return null;
    }

    @PreAuthorize("@authHelper.idMatches(#id, #authToken)")
    public Set<Project> findAllByOwnerId(UUID id) {
        return projectRepo.findAllByOwnerId(id);
    }

    @PreAuthorize("projectRepo.findById(id).getMembers().contains(authHelper.getPrincipalId())")
    public Optional<Project> getProject(UUID id, String authToken) {
        return projectRepo.findById(id);
    }
//    @PreAuthorize("projectRepo.findById(id).getMembers().contains(auth.getId)")
//    public Optional<Project> getProjectPublic(Integer id) {
//        try {
//            Project project = projectRepo.findById(id).get();
//        } catch (Exception NoSuchElementException) {
//            return
//        }
//    }

    @PreAuthorize("@authHelper.idMatches(#project.getOwnerId(), #authToken)")
    public ResponseEntity createProject(Project project, String authToken) {
        // first, check that the user who owns the project exists
        try {
            UUID ownerId = project.getOwnerId();
            User owner = userRepo.findById(ownerId).get();
            owner.addToInProjects(project);
            project.addToMembers(owner);

            projectRepo.save(project);

            ProjectMember projectMember = projectMemberRepo.findOneByProjectIdAndMemberId(project.getId(), owner.getId());
            projectMember.setCanAssignTask(true);
            projectMember.setCanCreateTask(true);
            projectMember.setCanDeleteTask(true);
            projectMember.setCanEditTask(true);
            projectMember.setCanSetDueDate(true);

            projectMemberRepo.save(projectMember);

            return new ResponseEntity(projectRepo.getById(project.getId()), HttpStatus.CREATED);
        } catch (Exception NoSuchElementException) {
            System.out.println(NoSuchElementException);
            return new ResponseEntity("User does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("@authHelper.idMatches(#project.getOwnerId(), #authToken)")
    public ResponseEntity updateProject(Project project) {
        // check that project exists
        if (!projectRepo.existsById(project.getId())) {
            return new ResponseEntity("Project does not exist", HttpStatus.BAD_REQUEST);
        }
        // you CANNOT add members using this function; have to use the addMember() function
        projectRepo.save(project);
        return new ResponseEntity(project, HttpStatus.OK);
    }

    @PreAuthorize("(project.getMembers().contains(authHelper.getPrincipalId)) && (project.getOwnerId() == authHelper.getPrincipalId())")
    public ResponseEntity addMember(UUID projectId, UUID userId) {
        // check that project exists
        try {
            Project project = projectRepo.getById(projectId);
            Set<User> members = project.getMembers();
            User addedUser = userRepo.findById(userId).get();
            // if the added user is already in the project, just return
            if (members.contains(addedUser)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            // otherwise, move on to adding the user
            project.addToMembers(addedUser);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.idMatches(#ownerId, #authToken)")
    public ResponseEntity deleteProject(UUID id) {
        try {
            UUID ownerId = projectRepo.findById(id).get().getOwnerId();
            // delete the project if it exists
            projectRepo.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // todo: try editing methods that return users, so that they only return some of the data about them. we can't have people getting other users' hashes from request/response body
    @PreAuthorize("projectRepo.findById(id).get().getMembers().contains(authHelper.getPrincipalId())")
    public Set<User> getMembers(UUID id) {
        Optional<Project> project = projectRepo.findById(id);
        return project.map(Project::getMembers).orElse(null);
    }
}
