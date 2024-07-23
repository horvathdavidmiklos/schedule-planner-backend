package com.scheduleplanner.authorization.createaccount.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Accessors(chain = true, fluent = true)
@Setter
public class NewAccount {
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;
    private String username;
}
