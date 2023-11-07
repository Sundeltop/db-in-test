package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    private final DataSourceConfig config = new DataSourceConfig();

    public DataSourceFactory postgres() {
        config.setDriverClassName("org.postgresql.Driver");
        return this;
    }

    public DataSourceFactory password(String password) {
        config.setPassword(password);
        return this;
    }

    public DataSourceFactory username(String username) {
        config.setUsername(username);
        return this;
    }

    public DataSourceFactory url(String url) {
        config.setUrl(url);
        return this;
    }

    public DataSource build() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(config.getDriverClassName());
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());

        return new HikariDataSource(hikariConfig);
    }
}
