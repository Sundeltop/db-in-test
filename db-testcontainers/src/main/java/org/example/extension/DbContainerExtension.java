package org.example.extension;

import org.example.annotation.DbContainer;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class DbContainerExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext extensionContext) throws IllegalAccessException {
        Field[] fields = testInstance.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(DbContainer.class)
                    && field.getType().isAssignableFrom(GenericContainer.class)) {
                field.setAccessible(true);
                field.set(testInstance, createContainer(field.getAnnotation(DbContainer.class)));
            }
        }
    }

    public GenericContainer<?> createContainer(DbContainer containerAnnotation) {
        return new GenericContainer<>(DockerImageName.parse(containerAnnotation.image()))
                .withEnv(Map.of("POSTGRES_PASSWORD", containerAnnotation.password()))
                .withCopyFileToContainer(
                        MountableFile.forClasspathResource(containerAnnotation.copiedResourceName()), "/docker-entrypoint-initdb.d/")
                .withExposedPorts(Arrays.stream(containerAnnotation.exposedPorts()).boxed().toArray(Integer[]::new))
                .withNetwork(Network.SHARED)
                .waitingFor(Wait.forListeningPort());
    }
}
