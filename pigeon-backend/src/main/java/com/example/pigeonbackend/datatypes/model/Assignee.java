package com.example.pigeonbackend.datatypes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="assignees", schema="project_data")
public class Assignee {
    @Id

    @Column
    private UUID assigneeId;
    @Column
    private UUID taskId;

    public void setAssigneeId(UUID assignee_id)  { this.assigneeId=assignee_id; }
}
