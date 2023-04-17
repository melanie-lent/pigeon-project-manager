package com.example.pigeonbackend.service;

import com.example.pigeonbackend.JwtUtils;
import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.ProjectMember;
import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.repo.ProjectMemberRepo;
import com.example.pigeonbackend.repo.ProjectRepo;
import com.example.pigeonbackend.repo.TaskRepo;
import com.example.pigeonbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;


@Service
public class AuthenticatedUserService {
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ProjectMemberRepo projectMemberRepo;
    @Autowired
    private JwtUtils jwtUtils;


    public boolean idMatches(UUID id, String token){
        String tokenId =  jwtUtils.getIdFromToken(token).toString();
        return id.toString().equals(tokenId);
    }

    public UUID getIdFromToken(String token) {
        return jwtUtils.getIdFromToken(token);
    }

    public boolean isInProject(UUID projectId, String token) {
        try {
            UUID tokenId = jwtUtils.getIdFromToken(token);
            Project project = projectRepo.findById(projectId).get();
            Set<User> members = project.getMembers();
            User member = userRepo.findById(tokenId).get();
            return members.contains(member);
        } catch (Exception NoSuchElementException) {
            System.out.println(NoSuchElementException);
            throw NoSuchElementException;
        }
    }

    public boolean canAccessTask(UUID taskId, String token) {
        try {
            Task task = taskRepo.findById(taskId).get();
            return isInProject(task.getProjectId(), token);
        } catch (Exception NoSuchElementException) {
            throw NoSuchElementException;
        }
    }

    public boolean sufficientPerms(UUID projectId, String token, String permType) {
        try {
            return true;
//            if (isInProject(projectId, token)) {
//                ProjectMember projectMember = projectMemberRepo.findByMemberIdAndProjectId(jwtUtils.getIdFromToken(token), projectId);
//                if (permType.equals("canCreateTask")) return projectMember.getCanCreateTask();
//                else if (permType.equals("canEditTask")) return projectMember.getCanEditTask();
//                else if (permType.equals("canDeleteTask")) return projectMember.getCanDeleteTask();
//                else if (permType.equals("canAssignTask")) return projectMember.getCanAssignTask();
//            }
//            return false;
        } catch (Exception NoSuchElementException) {
            throw NoSuchElementException;
        }
    }

    public Project getProjectFromTaskId(UUID taskId) {
        try {
            Task task = taskRepo.findById(taskId).get();
            return projectRepo.findById(task.getProjectId()).get();
        } catch (Exception NoSuchElementException) {
            throw NoSuchElementException;
        }
    }

    public Boolean isOwner(UUID projectId, String authToken) {
        try {
            Project project = projectRepo.findById(projectId).get();
            return project.getOwnerId().equals(getIdFromToken(authToken));
        } catch (Exception NoSuchElementException) {
            throw NoSuchElementException;
        }
    }
}
