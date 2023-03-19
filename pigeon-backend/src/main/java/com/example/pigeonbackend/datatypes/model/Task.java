package com.example.pigeonbackend.datatypes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name="tasks", schema="project_data")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;
    @Column
    private Integer createdBy;
    @Column
    private Integer projectId;
    @Column
    private String taskName;
    @Column
    private String description;
    @Column
    private Integer priority;
    @Column
    private Timestamp dueDate;
    @Column(name="last_edited", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastEdited;
    @Column(name="created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdOn;

//    @ManyToOne
//    @JoinColumn(name="projectId", referencedColumnName = "projectId", insertable = false, updatable = false)
//    private Project taskOfProject;

//    @ManyToMany(targetEntity = User.class, mappedBy = "assignedTasks")
//    private Set<User> assignees;

    public Task(Integer created_by, Integer project_id, String task_name, String description, Integer priority) {
        // todo: when creating a task, the parent project and creating user must exist. deletions of users and parent tasks (in future) do not cascade, unless it is the project owner that is deleted. deletions of projects do cascade.
        super();
        this.createdBy=created_by;
        this.projectId=project_id;
        this.taskName=task_name;
        this.description=description;
        this.priority=priority;
        this.createdOn=(Timestamp) Date.from(Instant.now());
    }

    // TODO: 2/14/2023 Create sanitization for the set functions
    // TODO: 2/23/2023 get many-many relationship with users and tasks working properly
    // TODO: 2/23/2023 add many-many relationship for parent and child tasks. have deletions cascade

    public void setTaskName(String task_name) {
        this.taskName=task_name;
    }

    public void setPriority(Integer priority) { this.priority=priority; }

    public void setDueDate(Timestamp due_date) {
        this.dueDate=due_date;
    }

    public void setLastEdited(Timestamp last_edited) {
        this.lastEdited=last_edited;
    }
}
