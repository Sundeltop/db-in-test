package org.example;

import lombok.SneakyThrows;
import org.example.mapper.AbstractResultSetMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class UserRepository {

    private final DataSource source;

    public UserRepository(DataSource source) {
        this.source = source;
    }

    @SneakyThrows
    public <T> T queryForObject(String sqlQuery, AbstractResultSetMapper<T> mapper) {
        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement()) {

            return mapper.map(statement.executeQuery(sqlQuery));
        }
    }
}
