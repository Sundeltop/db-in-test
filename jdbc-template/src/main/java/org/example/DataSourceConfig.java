package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class DataSourceConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
