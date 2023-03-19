package com.example.pigeonbackend.datatypes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="assignees", schema="project_data")
public class Assignee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer assigneeId;
    @Column
    private Integer taskId;

    public void setAssigneeId(Integer assignee_id)  { this.assigneeId=assignee_id; }
}
