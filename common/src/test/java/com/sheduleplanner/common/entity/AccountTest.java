package com.sheduleplanner.common.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {
    private static final String USERNAME = "TEST_USER";
    private static final String PASSWORD_HASH = "TEST_PASSWORD";
    @Test
    void createEntity() {
        var account = new Account().username(USERNAME).passwordHash(PASSWORD_HASH);
        assertThat(account).hasNoNullFieldsOrProperties();
    }
}
