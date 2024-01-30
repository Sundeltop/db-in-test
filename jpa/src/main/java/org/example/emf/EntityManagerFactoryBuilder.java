package org.example.emf;

import org.example.emf.config.ConnectionConfig;

import javax.persistence.EntityManagerFactory;

public class EntityManagerFactoryBuilder {

    private final ConnectionConfig config = new ConnectionConfig();

    public EntityManagerFactoryBuilder postgres() {
        config.setDriverClassName("org.postgresql.ds.PGSimpleDataSource");
        config.setDialect("org.hibernate.dialect.PostgreSQL94Dialect");
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
