package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.Task;
import com.example.pigeonbackend.datatypes.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecifications {
    @Autowired
    private EntityManager em;

    public List<Task> search(Task params) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> root = cq.from(Task.class);
        EntityType<Task> type = em.getMetamodel().entity(Task.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params.getId() != null) {
            predicates.add(cb.equal(root.get("id"), params.getId()));
        }

        if (params.getCreatedBy() != null) {
            predicates.add(cb.equal(root.get("createdBy"), params.getCreatedBy()));
        }

        if (params.getProjectId() != null) {
            predicates.add(cb.equal(root.get("projectId"), params.getProjectId()));
        }

        if (params.getTaskName() != null) {
            predicates.add(cb.like(root.get(type.getDeclaredSingularAttribute("taskName", String.class)), cb.lower(cb.literal("%" + params.getTaskName() + "%"))));
        }

        if (params.getDescription() != null) {
            predicates.add(cb.like(root.get(type.getDeclaredSingularAttribute("description", String.class)), cb.lower(cb.literal("%" + params.getDescription() + "%"))));
        }

        if (params.getPriority() != null) {
            predicates.add(cb.equal(root.get("priority"), params.getPriority()));
        }

        if (params.getDueDate() != null) {
            predicates.add(cb.equal(root.get("dueDate"), params.getDueDate()));
        }

        if (params.getLastEdited() != null) {
            predicates.add(cb.equal(root.get("lastEdited"), params.getLastEdited()));
        }

        if (params.getCreatedOn() != null) {
            predicates.add(cb.equal(root.get("createdOn"), params.getCreatedOn()));
        }

//        if (params.getTags() != null) {
//            predicates.add(cb.in(root.get("tags"), params.getTags());
//        }
//
//        if (params.getAssignees() != null) {
//            predicates.add(cb.in(root.get("assignees"), params.getAssignees());
//        }

        cq.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).orderBy(cb.desc(root.get("taskName"))).getRestriction();

        return em.createQuery(cq).getResultList();

        //todo: make sure sql injections dont work
        // TODO: 2/23/2023 add assignees to the search criteria
        // todo: add tags to search criteria
    }
    public List<User> getAssignees(Task task) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Join<User, Task> t = root.join("assignees");
        cq.where(cb.equal(t.get("assignees"), task.getId()));

        return em.createQuery(cq).getResultList();
    }
}
