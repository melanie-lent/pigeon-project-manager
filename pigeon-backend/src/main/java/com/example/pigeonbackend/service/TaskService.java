package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.repo.*;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskSpecifications ts;
    @Autowired
    private AuthHelper authHelper;

    public Boolean userIsInProject(UUID userId, UUID projectId) {
        try {
            User user = userRepo.findById(userId).get();
            Project project = projectRepo.findById(projectId).get();
            return project.getMembers().contains(user);
        } catch (Exception NoSuchElementException) {
            return false;
        }
    }

    public Set<Task> getAllTasks() {
        return new HashSet<>(taskRepo.findAll());
    }

    public Set<Task> getTasksByProject(Project project) {
        return project.getTasks();
    }

//    public List<Task> getTasksByProjectAndTags(Project project, List<Tag> tags) {
//        Set<Task> allTasks = project.getTasks();
//        return allTasks.stream().filter( t -> t.getTags().contains(tags)).collect(Collectors.toList());
//    }

    public Optional<Task> getTask(UUID id) {
        return taskRepo.findById(id);
    }

    @PreAuthorize("task.getCreatedBy() == authHelper.getPrincipalId()")
    public ResponseEntity<String> createTask(Task task) {
        // todo: user needs to be project member
        /* process:
        1. project exists
        2. creator exists
        3. creator is in project
        4. create project */
        UUID projectId = task.getProjectId();
        UUID userId = task.getCreatedBy();

        // first, ensure that the project the task is being created under exists
        if (!projectRepo.existsById(projectId)) {
            return new ResponseEntity<>("Project does not exist", HttpStatus.BAD_REQUEST);
        }
        // then, ensure that the user who is creating the task exists
        if (!userRepo.existsById(userId)) {
            return new ResponseEntity<>("Creator user does not exist", HttpStatus.BAD_REQUEST);
        }
        // check that the creator user is a member of the project
        if (!userIsInProject(userId, projectId)) {
            return new ResponseEntity<>("Creator user is not a member of project", HttpStatus.BAD_REQUEST);
        }
        // add tags to the tag table if they dont already exist
//        for (Task.Tag tag : task.getTags()) {
//            if (!tagRepo.existsByTagText(tag.getTagText())) {
//                tagRepo.save(tag);
//            }
//        }
//         lowercase all the tags
//        task.setTags(task.getTags().stream()
//                .map(String::toLowerCase)
//                .collect(Collectors.toList()));
        taskRepo.save(task);
        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }

    @PreAuthorize("task.getCreatedBy() == authHelper.getPrincipalId()")
    public ResponseEntity<Object> updateTask(Task task) {
        // check that task exists
        if (!taskRepo.existsById(task.getId())) {
            return new ResponseEntity<Object>("Task does not exist", HttpStatus.BAD_REQUEST);
        }
        taskRepo.save(task);
        return new ResponseEntity<Object>(task, HttpStatus.OK);
    }

    @PreAuthorize("projectRepo.findById(task.getProjectId()).members(contains(authHelper.getPrincipalId())) && ProjectMemberRepo.findByProjectIdAndMemberId(task.getProjectId(), user.getId()).getCanAssignTask()")
    public ResponseEntity<Object> addAssignee(User user, Task task) {
        try {
//            if (!userRepo.existsById(user.getId())) {
//                return new ResponseEntity<Object>("User does not exist", HttpStatus.BAD_REQUEST);
//            }
//            if (!userIsInProject(user.getId(), task.getProjectId())) {
//                return new ResponseEntity<Object>("User is not in given task's project", HttpStatus.BAD_REQUEST);
//            }
            // todo: implement perms
            Set<User> assignees = task.getAssignees();
            if (assignees.contains(user)) return new ResponseEntity<Object>(HttpStatus.OK);
            assignees.add(user);
            task.setAssignees(assignees);
            taskRepo.save(task);
            return new ResponseEntity<Object>("User added to task assignees", HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("projectRepo.findById(task.getProjectId()).members(contains(authHelper.getPrincipalId())) && ProjectMemberRepo.findByProjectIdAndMemberId(task.getProjectId(), user.getId()).getCanAssignTask()")
    public ResponseEntity<Object> removeAssignee(User user, Task task) {
        try {
//            if (!userRepo.existsById(user.getId())) {
//                return new ResponseEntity<Object>("User does not exist", HttpStatus.BAD_REQUEST);
//            }
//            if (!userIsInProject(user.getId(), task.getProjectId())) {
//                return new ResponseEntity<Object>("User is not in given task's project", HttpStatus.BAD_REQUEST);
//            }
            Set<User> assignees = task.getAssignees();
            if (!assignees.contains(user)) return new ResponseEntity<Object>(HttpStatus.OK);
            assignees.remove(user);
            task.setAssignees(assignees);
            taskRepo.save(task);
            return new ResponseEntity<Object>("User removed from task assignees", HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> addTag(Task task, String tag) {
//        if (!taskRepo.existsById(task.getId())) {
//            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
//        }
        try {
            // todo: implement perms
            List<String> tags = task.getTags();
            if (tags.contains(tag)) return new ResponseEntity<Object>(HttpStatus.OK);
            tags.add(tag);
            task.setTags(tags);
            taskRepo.save(task);
            return new ResponseEntity<Object>("Tag added to task", HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> removeTag(Task task, String tag) {
//        if (!taskRepo.existsById(task.getId())) {
//            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
//        }
        try {
            // todo: implement perms
            List<String> tags = task.getTags();
            if (!tags.contains(tag)) return new ResponseEntity<Object>(HttpStatus.OK);
            tags.remove(tag);
            task.setTags(tags);
            taskRepo.save(task);
            return new ResponseEntity<Object>("Tag removed from task", HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity deleteTask(UUID id) {
        // check that task exists
//        if (!taskRepo.existsById(id)) {
//            return new ResponseEntity<>("Task does not exist", HttpStatus.BAD_REQUEST);
//        }
        try {
            taskRepo.deleteById(id);
            return new ResponseEntity("Task deleted successfully", HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // search for tasks in the db that share attributes with the passed-in task
    public List<Task> getTasksByParams(Task task) {
        try {
            return ts.search(task);
        } catch (Exception NoSuchElementException) {
            return new ArrayList<>();
        }
    }

    public Set<User> getAssignees(Task task) {
        return task.getAssignees();
    }

//    public List<Task> getTasksByTags(UUID id) {
//
//    }
}
