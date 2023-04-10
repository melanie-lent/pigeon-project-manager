package com.example.pigeonbackend.datatypes.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

//@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="project_members", schema="project_data")
public class ProjectMember {
    @Id
    @Column
    private UUID projectId;
    @Column
    private UUID memberId;
    @Column
    private Boolean canCreateTask;
    @Column
    private Boolean canEditTask;
    @Column
    private Boolean canDeleteTask;
    @Column
    private Boolean canAssignTask;
    @Column
    private Boolean canSetDueDate;

    public void setCanAssignTask(Boolean canAssignTask) {
        this.canAssignTask = canAssignTask;
    }

    public void setCanCreateTask(Boolean canCreateTask) {
        this.canCreateTask = canCreateTask;
    }

    public void setCanEditTask(Boolean canEditTask) {
        this.canEditTask = canEditTask;
    }

    public void setCanDeleteTask(Boolean canDeleteTask) {
        this.canDeleteTask = canDeleteTask;
    }

    public void setCanSetDueDate(Boolean canSetDueDate) {
        this.canSetDueDate = canSetDueDate;
    }

    public Boolean getCanAssignTask() {
        return canAssignTask;
    }

    public Boolean getCanCreateTask() {
        return canCreateTask;
    }

    public Boolean getCanEditTask() {
        return canEditTask;
    }

    public Boolean getCanDeleteTask() {
        return canDeleteTask;
    }

    public Boolean getCanSetDueDate() {
        return canSetDueDate;
    }
}
