package com.example.pigeonbackend.datatypes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="project_members", schema="project_data")
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer project_id;
    @Column
    private Integer member_id;
    @Column
    private Boolean can_create_task;
    @Column
    private Boolean can_edit_task;
    @Column
    private Boolean can_delete_task;
    @Column
    private Boolean can_assign_task;
    @Column
    private Boolean can_set_due_date;

    public void setCanCreateTask(Boolean perm)  { this.can_create_task=perm; }
    public void setCanEditTask(Boolean perm)  { this.can_edit_task=perm; }
    public void setCanDeleteTask(Boolean perm)  { this.can_delete_task=perm; }
    public void setCanAssignTask(Boolean perm)  { this.can_assign_task=perm; }
    public void setCanSetDueDate(Boolean perm)  { this.can_set_due_date=perm; }
}
