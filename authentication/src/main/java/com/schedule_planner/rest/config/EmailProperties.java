package com.schedule_planner.rest.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailProperties {
    private String host;
    private String address;
    private String password;
}
