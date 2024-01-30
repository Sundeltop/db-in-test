package org.example.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.datasource.config.DataSourceConfig;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public enum DataSourceContext {
    INSTANCE;

    private final Map<DataSourceConfig, DataSource> store = new HashMap<>();

    public synchronized DataSource getDataSource(DataSourceConfig config) {
        if (store.containsKey(config)) {
            return store.get(config);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setDriverClassName(config.getDriverClassName());
        hikariConfig.setMaximumPoolSize(1);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        store.put(config, dataSource);
        return dataSource;
    }
}
