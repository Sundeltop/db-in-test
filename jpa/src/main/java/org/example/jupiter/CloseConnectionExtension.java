package org.example.jupiter;

import org.example.emf.EntityManagerFactoryContext;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import static org.junit.jupiter.api.extension.ExtensionContext.Store;

public class CloseConnectionExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        context.getRoot().getStore(Namespace.GLOBAL).getOrComputeIfAbsent(CloseConnectionCallback.class);
    }

    private static class CloseConnectionCallback implements Store.CloseableResource {

        @Override
        public void close() {
            for (EntityManagerFactory emf : EntityManagerFactoryContext.INSTANCE.getStoredEntityManagerFactories()) {
                if (emf != null && emf.isOpen()) {
                    emf.close();
                }
            }
        }
    }
}
