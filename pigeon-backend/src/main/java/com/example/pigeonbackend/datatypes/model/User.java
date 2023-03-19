package com.example.pigeonbackend.datatypes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@DynamicUpdate
@Table(name="users", schema="user_data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String hash;

//    private String email;
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(name="created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdOn;
    @Column
    private Timestamp lastLogin;
//    @ManyToMany(targetEntity = Project.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.IGNORE)
//    @JsonIgnoreProperties("members")
//    @JoinTable(
//            name = "project_members",
//            schema = "project_data",
//            joinColumns = @JoinColumn(name="memberId"),
//            inverseJoinColumns = @JoinColumn(name="projectId")
//    )

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("inProjects")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(
            name="project_members",
            schema = "project_data",
            joinColumns = @JoinColumn(name="memberId"),
            inverseJoinColumns = @JoinColumn(name="ProjectId")
    )
    private Set<Project> inProjects;
//    @ManyToMany(targetEntity = Task.class, fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.IGNORE)
//    @JoinTable(
//            name="assignees",
//            schema = "project_data",
//            joinColumns = {
////                    @JoinColumn(name="project_id"),
//                    @JoinColumn(name="assigneeId")
//            },
//            inverseJoinColumns = @JoinColumn(name="taskId")
//    )
//    private Set<Task> assignedTasks;

    public User(String username, String hash, String first_name, String last_name) {
        super();
        System.out.println(String.format("hellohello", hash,username,first_name,last_name));
        this.hash=hash;
//        this.email=email;
        this.username=username;
        this.firstName=first_name;
        this.lastName=last_name;
        this.createdOn=(Timestamp) Date.from(Instant.now());
    }

    // TODO: 2/14/2023 Create sanitization for the set functions

    public void setHash(String hash) {
        this.hash=hash;
    }


    public void setUsername(String username) { this.username=username; }

    public String getUsername() { return this.username; }

//    public void setEmail(String hash) {
//        this.email=email;
//    }

    public Set<Project> getInProjects() {
        return this.inProjects;
    }

    public void addToInProjects(Project project) {
        if (!this.inProjects.contains(project)) {
            this.inProjects.add(project);
        }
    }

    public void setFirstName(String first_name) {
        this.firstName=first_name;
    }

    public void setLastName(String last_name) {
        this.lastName=last_name;
    }

    public void setLastLogin() {
        this.lastLogin=(Timestamp) Date.from(Instant.now());
    }

//    public String toString() {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        return gson.toJson(this);
//    }
}
