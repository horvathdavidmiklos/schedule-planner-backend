package com.scheduleplanner.createaccount.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountInDtoTest {
    @Test
    void createDto() {
        var dto = new AccountInDto("USERNAME","EMAIL","PASSWORD","PASSWORD_CONFIRMATION");
        assertThat(dto).isNotNull();
    }


}
