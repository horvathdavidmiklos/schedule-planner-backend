package com.scheduleplanner.dataaccesslayer.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {
    public String url;
    public int port;
    public String schema;
    public String username;
    public String password;

}