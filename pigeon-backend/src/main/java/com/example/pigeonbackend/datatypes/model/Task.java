package com.example.pigeonbackend.datatypes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Data
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name="tasks", schema="project_data")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private UUID createdBy;
    @Column
    private UUID projectId;
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

    @ElementCollection
    @CollectionTable(name="task_tags", schema="project_data")
    private Set<String> tags = new HashSet<>();

    @ManyToMany(mappedBy = "assignedTasks", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> assignees = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name="tags",
//            schema = "project_data",
//            joinColumns = @JoinColumn(name="tagId")
//    )

//    @Embeddable
//    public class Tag {
//        private Integer id;
//        private String tagText;
//    }

    public Task(UUID created_by, UUID project_id, String task_name, String description, Integer priority) {
        super();
        this.createdBy=created_by;
        this.projectId=project_id;
        this.taskName=task_name;
        this.description=description;
        this.priority=priority;
        this.createdOn=(Timestamp) Date.from(Instant.now());
        this.assignees = new HashSet<>();
        this.tags = new HashSet<>();
    }

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

//    public List<String> getTags() {
//        return this.tags;
//    }
}
