package org.example;

import org.example.annotation.DbContainer;
import org.example.annotation.DbTestcontainers;
import org.example.dto.User;
import org.example.emf.EntityManagerFactoryBuilder;
import org.example.jupiter.CloseConnectionExtension;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(CloseConnectionExtension.class)
@DbTestcontainers
public class JpaTest {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "secret";
    private static final String EXPECTED_USER_NAME = "Peter";
    private static final String PERSISTENCE_UNIT_NAME = "test";

    private UserRepository userRepository;

    @DbContainer(password = DB_PASSWORD)
    private GenericContainer<?> container;

    @BeforeEach
    void setupEntityManagerFactory() {
        userRepository = new UserRepository(new EntityManagerFactoryBuilder()
                .postgres()
                .url("jdbc:postgresql://localhost:%d/postgres".formatted(container.getFirstMappedPort()))
                .username(DB_USERNAME)
                .password(DB_PASSWORD)
                .persistenceUnitName(PERSISTENCE_UNIT_NAME)
                .build()
                .createEntityManager());
    }

    @Test
    void verifyCreateUser() {
        userRepository.addUser(EXPECTED_USER_NAME);

        List<User> users = userRepository.getUsersByName(EXPECTED_USER_NAME);
        assertThat(users).first().extracting(User::getName).isEqualTo(EXPECTED_USER_NAME);
    }

    @Test
    void verifyUpdateUser() {
        userRepository.updateUserById(1, EXPECTED_USER_NAME);

        List<User> users = userRepository.getUsersByName(EXPECTED_USER_NAME);
        assertThat(users).first().extracting(User::getName).isEqualTo(EXPECTED_USER_NAME);
    }
}
