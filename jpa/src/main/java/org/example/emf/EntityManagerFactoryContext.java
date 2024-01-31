package org.example.emf;

import jakarta.persistence.EntityManagerFactory;
import org.example.emf.config.ConnectionConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static jakarta.persistence.Persistence.createEntityManagerFactory;


public enum EntityManagerFactoryContext {
    INSTANCE;

    private final Map<ConnectionConfig, EntityManagerFactory> store = new HashMap<>();

    public synchronized EntityManagerFactory getEntityManagerFactory(ConnectionConfig config) {
        if (store.containsKey(config)) {
            return store.get(config);
        }

        Map<String, String> settings = new HashMap<>();
        settings.put("hibernate.connection.url", config.getUrl());
        settings.put("hibernate.connection.username", config.getUsername());
        settings.put("hibernate.connection.password", config.getPassword());
        settings.put("hibernate.connection.driver_class", config.getDriverClassName());
        settings.put("hibernate.dialect", config.getDialect());

        EntityManagerFactory entityManagerFactory = createEntityManagerFactory(config.getPersistenceUnitName(), settings);

        store.put(config, entityManagerFactory);
        return entityManagerFactory;
    }

    public Collection<EntityManagerFactory> getStoredEntityManagerFactories() {
        return store.values();
    }
}
