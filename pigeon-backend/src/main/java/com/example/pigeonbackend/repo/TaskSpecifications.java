package com.example.pigeonbackend.repo;

import com.example.pigeonbackend.datatypes.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

        if (params.getCreated_by() != null) {
            predicates.add(cb.equal(root.get("created_by"), params.getCreated_by()));
        }

        if (params.getProject_id() != null) {
            predicates.add(cb.equal(root.get("project_id"), params.getProject_id()));
        }

        if (params.getTask_name() != null) {
            predicates.add(cb.like(root.get(type.getDeclaredSingularAttribute("task_name", String.class)), cb.lower(cb.literal("%" + params.getTask_name() + "%"))));
        }

        if (params.getDescription() != null) {
            predicates.add(cb.like(root.get(type.getDeclaredSingularAttribute("description", String.class)), cb.lower(cb.literal("%" + params.getDescription() + "%"))));
        }

        if (params.getPriority() != null) {
            predicates.add(cb.equal(root.get("priority"), params.getPriority()));
        }

        if (params.getDue_date() != null) {
            predicates.add(cb.equal(root.get("due_date"), params.getDue_date()));
        }

        if (params.getLast_edited() != null) {
            predicates.add(cb.equal(root.get("last_edited"), params.getLast_edited()));
        }

        if (params.getCreated_on() != null) {
            predicates.add(cb.equal(root.get("created_on"), params.getCreated_on()));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).orderBy(cb.desc(root.get("task_name"))).getRestriction();

        TypedQuery<Task> query = em.createQuery(cq);

        //todo: make sure sql injections dont work

        return query.getResultList();
    }
}
