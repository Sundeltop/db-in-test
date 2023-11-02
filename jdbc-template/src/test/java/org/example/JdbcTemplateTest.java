package org.example;

import org.example.annotation.DbContainer;
import org.example.annotation.DbTestcontainers;
import org.example.dto.User;
import org.example.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DbTestcontainers
public class JdbcTemplateTest {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "secret";
    private JdbcTemplate jdbcTemplate;

    @DbContainer(password = DB_PASSWORD)
    private GenericContainer<?> container;

    @BeforeEach
    void setupConnection() {
        jdbcTemplate = new JdbcTemplate(new DataSourceFactory()
                .postgres()
                .url("jdbc:postgresql://localhost:%d/postgres".formatted(container.getFirstMappedPort()))
                .username(DB_USERNAME)
                .password(DB_PASSWORD)
                .build());
    }

    @Test
    void verifySelectUser() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users", new UserMapper());

        assertThat(users).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void verifyDockerContainerIsRunning() {
        assertThat(container.isRunning()).isTrue();
    }
}
