package org.example;

import org.example.emf.EntityManagerFactoryBuilder;
import org.example.jupiter.CloseConnectionExtension;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CloseConnectionExtension.class)
public class JpaTest {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "secret";
    private static final String PERSISTENCE_UNIT_NAME = "test";

    private UserRepository userRepository;

    @BeforeEach
    void setupEntityManagerFactory() {
        userRepository = new UserRepository(new EntityManagerFactoryBuilder()
                .postgres()
                .url("jdbc:postgresql://localhost:%d/postgres".formatted(5432))
                .username(DB_USERNAME)
                .password(DB_PASSWORD)
                .persistenceUnitName(PERSISTENCE_UNIT_NAME)
                .build()
                .createEntityManager());
    }

    @Test
    void verifyCreateUser() {
        userRepository.addUser("Tom");
    }

    @Test
    void verifyUpdateUser() {
        userRepository.updateUserById(0, "Jack");
    }
}
