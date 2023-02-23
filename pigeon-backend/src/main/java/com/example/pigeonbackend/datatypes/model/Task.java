package com.example.pigeonbackend.datatypes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@Table(name="tasks", schema="project_data")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer created_by;
    @Column
    private Integer project_id;
    @Column
    private String task_name;
    @Column
    private String description;
    @Column
    private Integer priority;
    @Column
    private Timestamp due_date;
    @Column(name="last_edited", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp last_edited;
    @Column(name="created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp created_on;
    public Task(Integer created_by, Integer project_id, String task_name, String description, Integer priority) {
        // todo: when creating a task, the parent project and creating user must exist. deletions of users and parent tasks (in future) do not cascade, unless it is the project owner that is deleted. deletions of projects do cascade.
        super();
        this.created_by=created_by;
        this.project_id=project_id;
        this.task_name=task_name;
        this.description=description;
        this.priority=priority;
        this.created_on=(Timestamp) Date.from(Instant.now());
    }

    // TODO: 2/14/2023 Create sanitization for the set functions

//    public void setProjectId(Integer project_id) { this.project_id=project_id; }

    public void setTaskName(String task_name) {
        this.task_name=task_name;
    }

    public void setPriority(Integer priority) { this.priority=priority; }

    public void setDueDate(Timestamp due_date) {
        this.due_date=due_date;
    }

    public void setLastEdited(Timestamp last_edited) {
        this.last_edited=last_edited;
    }
}
