package com.scheduleplanner.common.email;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailConfig {
    private String host;
    private String address;
    private String password;
}
