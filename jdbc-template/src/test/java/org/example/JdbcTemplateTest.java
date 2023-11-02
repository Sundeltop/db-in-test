package org.example;

import org.example.dto.User;
import org.example.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.Network.SHARED;
import static org.testcontainers.containers.wait.strategy.Wait.forListeningPort;
import static org.testcontainers.utility.MountableFile.forClasspathResource;

@Testcontainers
public class JdbcTemplateTest {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "secret";

    @Container
    private final GenericContainer<?> container =
            new GenericContainer<>(DockerImageName.parse("postgres"))
                    .withEnv(Map.of("POSTGRES_PASSWORD", DB_PASSWORD))
                    .withCopyFileToContainer(
                            forClasspathResource("init-db.sql"), "/docker-entrypoint-initdb.d/")
                    .withExposedPorts(5432)
                    .withNetwork(SHARED)
                    .waitingFor(forListeningPort());

    private static JdbcTemplate jdbcTemplate;

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
        System.out.println(container.getFirstMappedPort());
        List<User> users = jdbcTemplate.query("SELECT * FROM users", new UserMapper());

        assertThat(users).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void verifyDockerContainerIsRunning() {
        System.out.println(container.getFirstMappedPort());
        assertThat(container.isRunning()).isTrue();
    }
}
