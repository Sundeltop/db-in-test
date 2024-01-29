package org.example;

import javax.sql.DataSource;

public class DataSourceBuilder {

    private final DataSourceConfig config = new DataSourceConfig();

    public DataSourceBuilder postgres() {
        config.setDriverClassName("org.postgresql.Driver");
        return this;
    }

    public DataSourceBuilder password(String password) {
        config.setPassword(password);
        return this;
    }

    public DataSourceBuilder username(String username) {
        config.setUsername(username);
        return this;
    }

    public DataSourceBuilder url(String url) {
        config.setUrl(url);
        return this;
    }

    public DataSource build() {
        return DataSourceContext.INSTANCE.getDataSource(config);
    }
}
