package org.example.emf.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ConnectionConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String dialect;
    private String persistenceUnitName;
}
