package org.example.datasource.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DataSourceConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
