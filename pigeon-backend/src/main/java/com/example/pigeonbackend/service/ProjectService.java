package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.ProjectMember;
import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.repo.ProjectMemberRepo;
import com.example.pigeonbackend.repo.ProjectRepo;
import com.example.pigeonbackend.repo.TaskRepo;
import com.example.pigeonbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectMemberRepo projectMemberRepo;
    @Autowired
    private AuthenticatedUserService authHelper;
    @Autowired
    private TaskRepo taskRepo;

//    public List<Project> getAllProjects() {
//        System.out.println("get all projects");
//        List<Project> projects = projectRepo.findAll();
//        return projects;
//        return null;
//    }

//    @PreAuthorize("@authHelper.idMatches(#id, #authToken)")
//    public Set<Project> findAllByOwnerId(UUID id) {
//        return projectRepo.findAllByOwnerId(id);
//    }

//    @PreAuthorize("projectRepo.findById(id).getMembers().contains(auth.getId)")
//    public Optional<Project> getProjectPublic(Integer id) {
//        try {
//            Project project = projectRepo.findById(id).get();
//        } catch (Exception NoSuchElementException) {
//            return
//        }
//    }

    @PreAuthorize("@authHelper.isInProject(#id, #authToken)")
    public Optional<Project> getProject(UUID id, String authToken) {
        try {
            return projectRepo.findById(id);
        } catch (Exception NoSuchElementException) {
            return null;
        }
    }

    @PreAuthorize("@authHelper.isInProject(#id, #authToken)")
    public Set<Task> getTasksByProject(UUID id, String authToken) {
        try {
            return taskRepo.findAllByProjectId(id);
        } catch (Exception NoSuchElementException) {
            return new HashSet<>();
        }
    }

    public ResponseEntity<String> createProject(Project project, String authToken) {
        // first, check that the user who owns the project exists
        try {
            UUID ownerId = authHelper.getIdFromToken(authToken);
            User owner = userRepo.findById(ownerId).get();
            owner.addToInProjects(project);
            project.setOwnerId(ownerId);
            project.addToMembers(owner);

            projectRepo.save(project);

            ProjectMember projectMember = projectMemberRepo.findByMemberIdAndProjectId(owner.getId(), project.getId());
            projectMember.setCanAssignTask(true);
            projectMember.setCanCreateTask(true);
            projectMember.setCanDeleteTask(true);
            projectMember.setCanEditTask(true);

            projectMemberRepo.save(projectMember);

            return new ResponseEntity(projectRepo.findById(project.getId()), HttpStatus.CREATED);
        } catch (Exception NoSuchElementException) {
            System.out.println(NoSuchElementException);
            return new ResponseEntity("User does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.idMatches(#project.getOwnerId(), #authToken)")
    public ResponseEntity updateProject(Project project, String authToken) {
        // check that project exists
        if (!projectRepo.existsById(project.getId())) {
            return new ResponseEntity("Project does not exist", HttpStatus.NOT_FOUND);
        }
        // you CANNOT add members using this function; have to use the addMember() function
        projectRepo.save(project);
        return new ResponseEntity(project, HttpStatus.OK);
    }

    @PreAuthorize("@authHelper.isOwner(#projectId, #authToken)")
    public ResponseEntity<String> addMember(UUID projectId, ProjectMember projectMember, String authToken) {
        // check that project exists
        try {
            Project project = projectRepo.findById(projectId).get();
            Set<User> members = project.getMembers();
            User addedUser = userRepo.findById(projectMember.getMemberId()).get();
            // if the added user is already in the project, just return
            if (members.contains(addedUser)) {
                return new ResponseEntity(project, HttpStatus.OK);
            }
            // otherwise, move on to adding the user
            project.addToMembers(addedUser);
            projectMemberRepo.save(projectMember);
            return new ResponseEntity(project, HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.isOwner(#projectId, #authToken)")
    public ResponseEntity<String> editPerms(UUID projectId, ProjectMember projectMember, String authToken) {
        try {
            Project project = projectRepo.findById(projectId).get();
            Set<User> members = project.getMembers();
            User addedUser = userRepo.findById(projectMember.getMemberId()).get();
            if (members.contains(addedUser)) {
                projectMemberRepo.save(projectMember);
            }
            return new ResponseEntity(projectMember, HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.isOwner(#projectId, #authToken)")
    public ResponseEntity<String> removeMember(UUID projectId, UUID userId, String authToken) {
        // check that project exists
        try {
            Project project = projectRepo.findById(projectId).get();
            Set<User> members = project.getMembers();
            User removedUser = userRepo.findById(userId).get();
            // if the removed user is already not in the project, just return
            if (!members.contains(removedUser)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            // otherwise, move on to removing the user
            project.removeMember(removedUser);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.idMatches(#ownerId, #authToken)")
    public ResponseEntity deleteProject(UUID id, String authToken) {
        try {
            UUID ownerId = projectRepo.findById(id).get().getOwnerId();
            // delete the project if it exists
            projectRepo.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.isInProject(#id, #authToken)")
    public Set<User> getMembers(UUID id, String authToken) {
        try {
            Optional<Project> project = projectRepo.findById(id);
            return project.map(Project::getMembers).orElse(null);
        } catch (Exception NoSuchElementException) {
            return new HashSet<>();
        }
    }

}
