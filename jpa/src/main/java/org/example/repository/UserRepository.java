package org.example.repository;

import org.example.dto.User;

import javax.persistence.EntityManager;

public class UserRepository extends JpaRepository {

    public UserRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public void addUser(String name) {
        User user = new User();
        user.setName(name);
        persist(user);
    }

    public void updateUserById(int id, String newName) {
        User user = entityManager.find(User.class, id);
        user.setName(newName);
        merge(user);
    }
}
