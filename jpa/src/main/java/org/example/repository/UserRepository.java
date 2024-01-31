package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.dto.User;

import java.util.List;
import java.util.Optional;


public class UserRepository extends JpaRepository {

    public UserRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public void addUser(String name) {
        User user = new User();
        user.setName(name);
        persist(user);
    }

    public User getUserByName(String name) {
        return Optional.of(entityManager
                        .createQuery("select u from User u where u.name = :name", User.class)
                        .setParameter("name", name)
                        .getSingleResult())
                .orElseThrow();
    }

    public List<User> getUsersByName(String name) {
        return entityManager
                .createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public void updateUserById(int id, String newName) {
        User user = entityManager.find(User.class, id);
        user.setName(newName);
        merge(user);
    }
}
