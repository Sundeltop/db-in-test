package org.example.datasource;

import org.example.datasource.config.DataSourceConfig;

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
        return DataSourceContext.INSTANCE.getDataSource(config);
    }
}
