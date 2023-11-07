package org.example.mapper;

import org.example.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements AbstractResultSetMapper<List<User>> {

    @Override
    public List<User> map(ResultSet resultSet) throws SQLException {
        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new User(resultSet.getString("name")));
        }
        return result;
    }
}
