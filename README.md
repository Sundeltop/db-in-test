## To compile project:

First need to generate `db-testcontainers` **jar**. To do that:

* Run `mvn clean install` in `db-testcontainers` module

It will put generated jar in your local maven repository. After that you will be able to use it as follows:

```
<dependency>
<groupId>org.example</groupId>
<artifactId>db-testcontainers</artifactId>
<version>${project.version}</version>
<type>jar</type>
<scope>test</scope>
</dependency>
```