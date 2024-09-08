package com.scheduleplanner.common.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "email")
public class EmailProperties {
    private String host;
    private String address;
    private String password;

}
