package com.scheduleplanner.endpoint.createaccount;
import com.scheduleplanner.secret.AccountInDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountInDtoTest {
    @Test
    void test_createDto() {
        var dto = new AccountInDto("USERNAME","EMAIL","PASSWORD","PASSWORD_CONFIRMATION");
        assertThat(dto).isNotNull();
    }


}
