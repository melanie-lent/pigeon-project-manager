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
    private Integer assignee_id;
    @Column
    private Integer task_id;

    public void setAssigneeId(Integer assignee_id)  { this.assignee_id=assignee_id; }
}
