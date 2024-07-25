package com.sheduleplanner.common.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NewAccountTest {
    private static final String USERNAME = "TEST_USER";
    private static final String PASSWORD_HASH = "TEST_PASSWORD";
    private static final String EMAIL = "TEST_EMAIL@TEST.COM";
    private static final LocalDateTime CREATED_AT = LocalDate.of(2022, 1, 1).atTime(12,0);

    @Test
    void createEntity() {
        var newAccount = new NewAccount()
                .username(USERNAME)
                .passwordHash(PASSWORD_HASH)
                .createdAt(CREATED_AT)
                .email(EMAIL);
        assertThat(newAccount).hasNoNullFieldsOrProperties();
    }
}
