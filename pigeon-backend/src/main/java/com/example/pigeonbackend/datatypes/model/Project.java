package com.example.pigeonbackend.datatypes.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer ownerId;

    //    @ManyToMany(targetEntity = User.class, mappedBy = "inProjects", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("inProjects")
//    @ToString.Exclude
//    @NotFound(action = NotFoundAction.IGNORE)

    @ManyToMany(mappedBy = "inProjects", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> members = new HashSet<User>();

//    @OneToMany(targetEntity = Task.class, mappedBy = "taskOfProject", fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.IGNORE)
//    private Set<Task> tasks = new HashSet<Task>();

    public Project(String name, Integer owner_id) {
        super();
        this.name=name;
        this.ownerId=owner_id;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void addToMembers(User user) {
//        System.out.println(user);
        if (!this.members.contains(user)) {
            System.out.println("user is not already in member list");
            this.members.add(user);
        }
    }

    // todo: figure out how to add owner to members by default

//    public String toString() {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        return gson.toJson(this);
//    }
}