package org.example.annotation;

import org.testcontainers.junit.jupiter.Container;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Container
public @interface DbContainer {

    String image() default "postgres";

    String password();

    int[] exposedPorts() default {5432};

    String copiedResourceName() default "init-db.sql";
}
