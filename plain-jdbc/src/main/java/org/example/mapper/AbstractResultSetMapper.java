package org.example.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface AbstractResultSetMapper<T> {

    T map(ResultSet resultSet) throws SQLException;
}
