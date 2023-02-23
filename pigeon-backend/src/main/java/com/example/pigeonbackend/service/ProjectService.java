package com.example.pigeonbackend.service;

import com.example.pigeonbackend.datatypes.model.Project;
import com.example.pigeonbackend.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    // todo: users must exist for a project with a user as its creator in order to exist. deletions cascade.
    //todo: see if you can make the combination of project name and owner id have to be unique. ask russ
    @Autowired
    private ProjectRepo projectRepo;

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        projectRepo.findAll().forEach(projects::add);
        return projects;
    }

    public Optional<Project> getProject(Integer id) {
        return projectRepo.findById(id);
    }

    public void createProject(Project project) {
        // first, check that the user who owns the project exists

        projectRepo.save(project);
    }

    public void updateProject(Integer id, Project project) {
        projectRepo.save(project);
    }

    public void deleteProject(Integer id) {
        projectRepo.deleteById(id);
    }
}
