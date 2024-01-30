package org.example;

import org.example.annotation.DbContainer;
import org.example.annotation.DbTestcontainers;
import org.example.datasource.DataSourceBuilder;
import org.example.dto.User;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DbTestcontainers
public class PlainJdbcTest {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "secret";

    @DbContainer(password = DB_PASSWORD)
    private GenericContainer<?> container;

    private UserRepository repository;

    @BeforeEach
    void setupConnection() {
        repository = new UserRepository(new DataSourceBuilder()
                .postgres()
                .url("jdbc:postgresql://localhost:%d/postgres".formatted(container.getFirstMappedPort()))
                .username(DB_USERNAME)
                .password(DB_PASSWORD)
                .build());
    }

    @Test
    void verifySelectUser() {
        List<User> users = repository.queryForObject("SELECT * FROM users", new UserMapper());

        assertThat(users).hasSizeGreaterThanOrEqualTo(1);
    }
}
