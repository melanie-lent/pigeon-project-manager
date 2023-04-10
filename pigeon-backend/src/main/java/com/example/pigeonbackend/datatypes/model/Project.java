package com.example.pigeonbackend.datatypes.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Getter
@DynamicUpdate
@Setter
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@AllArgsConstructor
@NoArgsConstructor
@Table(name="projects", schema="project_data")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String name;
    @Column
    private UUID ownerId;

    //    @ManyToMany(targetEntity = User.class, mappedBy = "inProjects", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("inProjects")
//    @ToString.Exclude
//    @NotFound(action = NotFoundAction.IGNORE)

    @ManyToMany(mappedBy = "inProjects", fetch = FetchType.LAZY)
//    @JsonIgnore
    private Set<User> members;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="projectId")
    private Set<Task> tasks;

    public Project(String name, UUID owner_id) {
        super();
        this.name=name;
        this.ownerId=owner_id;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void addToMembers(User user) {
        if (!this.members.contains(user)) {
            this.members.add(user);
        }
    }

//    public String toString() {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        return gson.toJson(this);
//    }
}