package org.example.emf;

import jakarta.persistence.EntityManagerFactory;
import org.example.emf.config.ConnectionConfig;

public class EntityManagerFactoryBuilder {

    private final ConnectionConfig config = new ConnectionConfig();

    public EntityManagerFactoryBuilder postgres() {
        config.setDriverClassName("org.postgresql.Driver");
        config.setDialect("org.hibernate.dialect.PostgreSQLDialect");
        return this;
    }

    public EntityManagerFactoryBuilder password(String password) {
        config.setPassword(password);
        return this;
    }

    public EntityManagerFactoryBuilder username(String username) {
        config.setUsername(username);
        return this;
    }

    public EntityManagerFactoryBuilder url(String url) {
        config.setUrl(url);
        return this;
    }

    public EntityManagerFactoryBuilder persistenceUnitName(String persistenceUnitName) {
        config.setPersistenceUnitName(persistenceUnitName);
        return this;
    }

    public EntityManagerFactory build() {
        return new ThreadSafeEntityManagerFactory(EntityManagerFactoryContext.INSTANCE.getEntityManagerFactory(config));
    }
}
