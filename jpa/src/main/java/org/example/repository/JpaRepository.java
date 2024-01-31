package org.example.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.function.Consumer;

public abstract class JpaRepository {

    protected final EntityManager entityManager;

    public JpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> void persist(T entity) {
        transaction(em -> em.persist(entity));
    }

    public <T> void merge(T entity) {
        transaction(em -> em.merge(entity));
    }

    public <T> void remove(T entity) {
        transaction(em -> em.remove(entity));
    }

    public <T> void refresh(T entity) {
        entityManager.refresh(entity);
    }

    protected void transaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
