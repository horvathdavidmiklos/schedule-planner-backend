package com.scheduleplanner.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@Setter
public class Account {
    private String username;
    private String passwordHash;
}
