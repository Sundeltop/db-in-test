package org.example;

import org.example.annotation.DbContainer;
import org.example.annotation.DbTestcontainers;
import org.example.datasource.DataSourceBuilder;
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
    private static final String EXPECTED_USER_NAME = "Peter";
    private JdbcTemplate jdbcTemplate;

    @DbContainer(password = DB_PASSWORD)
    private GenericContainer<?> container;

    @BeforeEach
    void setupConnection() {
        jdbcTemplate = new JdbcTemplate(new DataSourceBuilder()
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
    void verifySelectUserCount() {
        Integer usersCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);

        assertThat(usersCount).isEqualTo(1);
    }

    @Test
    void verifyInsertUser() {
        jdbcTemplate.update("INSERT INTO users VALUES (?)", EXPECTED_USER_NAME);

        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE name = ?", new UserMapper(), EXPECTED_USER_NAME);

        assertThat(users).first().extracting(User::getName).isEqualTo(EXPECTED_USER_NAME);
    }

    @Test
    void verifyUpdateUser() {
        jdbcTemplate.update("UPDATE users SET name = ?", EXPECTED_USER_NAME);

        List<User> users = jdbcTemplate.query("SELECT * FROM users", new UserMapper());

        assertThat(users).first().extracting(User::getName).isEqualTo(EXPECTED_USER_NAME);
    }

    @Test
    void verifyDeleteUser() {
        jdbcTemplate.update("DELETE FROM users");

        Integer usersCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);

        assertThat(usersCount).isEqualTo(0);
    }

    @Test
    void verifyDockerContainerIsRunning() {
        assertThat(container.isRunning()).isTrue();
    }
}
