package com.scheduleplanner.encrypt;

import com.scheduleplanner.common.exception.baseexception.handled.EncryptException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EncryptTest {
    private static final String  PASSWORD= "PASSWORD_1234@!";
    private static final String  WRONG_PASSWORD= "WRONG_PASSWORD_123";

    private EncryptImpl encrypt;
    @BeforeEach void setUp(){
        encrypt = new EncryptImpl();
    }

    @Test void positive(){
        var hashed = encrypt.hashPassword(PASSWORD);
        assertThat(encrypt.checkPassword(PASSWORD, hashed)).isTrue();
    }

    @Test void wrong(){
        var hashed = encrypt.hashPassword(PASSWORD);
        assertThat(encrypt.checkPassword(WRONG_PASSWORD, hashed)).isFalse();
    }

    @Test void exception(){
        assertThatThrownBy(()->encrypt.checkPassword(WRONG_PASSWORD, "1234!"))
                .isExactlyInstanceOf(EncryptException.class);
    }


}
