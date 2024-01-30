package org.example.emf;

import org.example.emf.config.ConnectionConfig;

import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.Persistence.createEntityManagerFactory;

public enum EntityManagerFactoryContext {
    INSTANCE;

    private final Map<ConnectionConfig, EntityManagerFactory> store = new HashMap<>();

    public synchronized EntityManagerFactory getEntityManagerFactory(ConnectionConfig config) {
        if (store.containsKey(config)) {
            return store.get(config);
        }

        Map<String, String> settings = new HashMap<>();
        settings.put("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        settings.put("hibernate.hikari.dataSourceClassName", config.getDriverClassName());
        settings.put("hibernate.hikari.maximumPoolSize", "32");
        settings.put("hibernate.hikari.minimumIdle", "0");
        settings.put("hibernate.hikari.idleTimeout", "240000");
        settings.put("hibernate.hikari.maxLifetime", "270000");
        settings.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        settings.put("hibernate.hikari.dataSource.url", config.getUrl());
        settings.put("hibernate.hikari.dataSource.user", config.getUsername());
        settings.put("hibernate.hikari.dataSource.password", config.getPassword());
        settings.put("hibernate.dialect", config.getDialect());

        EntityManagerFactory entityManagerFactory = createEntityManagerFactory(config.getPersistenceUnitName(), settings);

        store.put(config, entityManagerFactory);
        return entityManagerFactory;
    }

    public Collection<EntityManagerFactory> getStoredEntityManagerFactories() {
        return store.values();
    }
}
