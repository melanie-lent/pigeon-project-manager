package com.example.pigeonbackend.datatypes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="projects", schema="project_data")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String project_name;
    private Integer owner_id;

    public Integer getId() {
        return id;
    }
    public void setProjectName(String project_name) {
        this.project_name=project_name;
    }
    public void setOwner(Integer owner_id) { this.owner_id=owner_id; }
}
