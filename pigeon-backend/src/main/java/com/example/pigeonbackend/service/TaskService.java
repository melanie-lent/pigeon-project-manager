package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.datatypes.model.User;
import com.example.pigeonbackend.repo.ProjectRepo;
import com.example.pigeonbackend.repo.TaskRepo;
import com.example.pigeonbackend.repo.TaskSpecifications;
import com.example.pigeonbackend.repo.UserRepo;
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
    private AuthenticatedUserService authHelper;

//    public Boolean userIsInProject(UUID userId, UUID projectId) {
//        try {
//            User user = userRepo.findById(userId).get();
//            Project project = projectRepo.findById(projectId).get();
//            return project.getMembers().contains(user);
//        } catch (Exception NoSuchElementException) {
//            return false;
//        }
//    }

//    public Set<Task> getAllTasks() {
//        return new HashSet<>(taskRepo.findAll());
//    }

//    @PreAuthorize("@authHelper.isInProject(#id, #token)")
//    public Set<Task> getTasksByProject(Project project, String authToken) {
//        return project.getTasks();
//    }

//    public List<Task> getTasksByProjectAndTags(Project project, List<Tag> tags) {
//        Set<Task> allTasks = project.getTasks();
//        return allTasks.stream().filter( t -> t.getTags().contains(tags)).collect(Collectors.toList());
//    }

    @PreAuthorize("@authHelper.canAccessTask(#id, #authToken)")
    public Optional<Task> getTask(UUID id, String authToken) {
        try {
            return taskRepo.findById(id);
        } catch (Exception NoSuchElementException) {
            return null;
        }
    }

    @PreAuthorize("(@authHelper.isInProject(#task.getProjectId(), #authToken) && @authHelper.sufficientPerms(#task.getProjectId(), #authToken, \"canCreateTask\"))")
    public ResponseEntity<String> createTask(Task task, String authToken) {
        try {
//            if (authHelper.isInProject(task.getProjectId(), authToken) && authHelper.sufficientPerms(task.getProjectId(), authToken, "canCreateTask")) {
//
//            }

            // check that the creator user is a member of the project
//        if (!userIsInProject(userId, projectId)) {
//            return new ResponseEntity<>("Creator user is not a member of project", HttpStatus.BAD_REQUEST);
//        }
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
            return new ResponseEntity(task, HttpStatus.CREATED);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity<>("Check that project and user exist", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.sufficientPerms(#task.getProjectId(), #authToken, \"canEditTask\")")
    public ResponseEntity<Object> updateTask(Task task, String authToken) {
        // check that task exists
        try {
            taskRepo.save(task);
            return new ResponseEntity<Object>(task, HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity<Object>("Task does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.sufficientPerms(@authHelper.getProjectFromTaskId(#taskId).getId(), #authToken, \"canAssignTask\")")
//    @PreAuthorize("projectRepo.findById(task.getProjectId()).members(contains(authHelper.getPrincipalId())) && ProjectMemberRepo.findByProjectIdAndMemberId(task.getProjectId(), user.getId()).getCanAssignTask()")
    public ResponseEntity<Object> addAssignee(UUID userId, UUID taskId, String authToken) {
        try {
            Task task = taskRepo.findById(taskId).get();
            User assignee = userRepo.findById(userId).get();
            Set<User> assignees = task.getAssignees();
            if (assignees.contains(assignee)) return new ResponseEntity<Object>(HttpStatus.OK);
            assignees.add(assignee);
            task.setAssignees(assignees);
            taskRepo.save(task);
            return new ResponseEntity<Object>("User added to task assignees", HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.sufficientPerms(#task.getProjectId(), #authToken, \"canAssignTask\")")
    public ResponseEntity<Object> removeAssignee(User user, Task task, String authToken) {
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

    @PreAuthorize("@authHelper.sufficientPerms(#taskId, #authToken, \"canEditTask\")")
    public ResponseEntity<Object> addTag(UUID taskId, String tag, String authToken) {
//        if (!taskRepo.existsById(task.getId())) {
//            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
//        }
        try {
            // todo: implement perms
            Task task = taskRepo.findById(taskId).get();
            Set<String> tags = task.getTags();
            if (tags.contains(tag)) return new ResponseEntity<Object>(HttpStatus.OK);
            tags.add(tag);
            task.setTags(tags);
            taskRepo.save(task);
            return new ResponseEntity<Object>("Tag added to task", HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("@authHelper.sufficientPerms(#taskId, #authToken, \"canEditTask\")")
    public ResponseEntity<Object> removeTag(Task task, String tag, String authToken) {
//        if (!taskRepo.existsById(task.getId())) {
//            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
//        }
        try {
            // todo: implement perms
            Set<String> tags = task.getTags();
            if (!tags.contains(tag)) return new ResponseEntity<Object>(HttpStatus.OK);
            tags.remove(tag);
            task.setTags(tags);
            taskRepo.save(task);
            return new ResponseEntity<Object>("Tag removed from task", HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

        @PreAuthorize("@authHelper.isInProject(@authHelper.getProjectFromTaskId(#id).getId(), #authToken)")
    public ResponseEntity deleteTask(UUID id, String authToken) {
        // check that task exists
//        if (!taskRepo.existsById(id)) {
//            return new ResponseEntity<>("Task does not exist", HttpStatus.BAD_REQUEST);
//        }
        try {
            Task task = taskRepo.findById(id).get();
            taskRepo.deleteById(id);
            return new ResponseEntity(task, HttpStatus.OK);
        } catch (Exception NoSuchElementException) {
            System.out.println(NoSuchElementException);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // search for tasks in the db that share attributes with the passed-in task
    @PreAuthorize("@authHelper.isInProject(#projectId, #authToken)")
    public List<Task> getTasksByParams(UUID projectId, Task task, String authToken) {
        try {
            return ts.search(projectId, task);
        } catch (Exception NoSuchElementException) {
            return new ArrayList<>();
        }
    }

    public Set<User> getAssignees(Task task, String authToken) {
        return task.getAssignees();
    }

//    public List<Task> getTasksByTags(UUID id) {
//
//    }
}
