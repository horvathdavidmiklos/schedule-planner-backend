package com.scheduleplanner.rest.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtProperties {
    private String secretKey;
}
