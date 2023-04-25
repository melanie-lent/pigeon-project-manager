package com.example.pigeonbackend.datatypes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@DynamicUpdate
@Table(name="users", schema="user_data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
//    @JsonIgnore
    @Column
    @JsonIgnore
    private String password;
    private String email;
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
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(
            name="project_members",
            schema = "project_data",
            joinColumns = @JoinColumn(name="memberId"),
            inverseJoinColumns = @JoinColumn(name="ProjectId")
    )
    private Set<Project> inProjects;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(
            name="assignees",
            schema = "project_data",
            joinColumns = @JoinColumn(name="assigneeId"),
            inverseJoinColumns = @JoinColumn(name="taskId")
    )
    private Set<Task> assignedTasks;

    public User(String username, String password, String email, String first_name, String last_name) {
        super();
        this.password=password;
        this.email=email;
        this.username=username;
        this.firstName=first_name;
        this.lastName=last_name;
        this.createdOn=(Timestamp) Date.from(Instant.now());
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public void setUsername(String username) { this.username=username; }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() { return this.username; }

    public void setEmail(String email) {
        this.email=email;
    }

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
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
}
