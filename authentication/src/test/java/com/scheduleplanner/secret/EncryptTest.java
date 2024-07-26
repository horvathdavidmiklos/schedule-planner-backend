package com.scheduleplanner.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EncryptTest {
    private static final String  PASSWORD= "PASSWORD_1234@!";
    private static final String  WRONG_PASSWORD= "WRONG_PASSWORD_123";

    @Test
    void test_encrypt_positive(){
        var hashed = Encrypt.hashPassword(PASSWORD);
        assertThat(Encrypt.checkPassword(PASSWORD, hashed)).isTrue();
    }

    @Test
    void test_encrypt_wrong(){
        var hashed = Encrypt.hashPassword(PASSWORD);
        assertThat(Encrypt.checkPassword(WRONG_PASSWORD, hashed)).isFalse();
    }


}
