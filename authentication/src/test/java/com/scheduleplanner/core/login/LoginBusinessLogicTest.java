package com.scheduleplanner.core.login;

import com.mysql.cj.log.Log;
import com.scheduleplanner.common.exception.baseexception.handled.EmailAddressNotFound;
import com.scheduleplanner.common.exception.baseexception.handled.UnverifiedAccountException;
import com.scheduleplanner.common.exception.baseexception.handled.WrongDataException;
import com.scheduleplanner.core.login.dto.LoginAccountInDto;
import com.scheduleplanner.core.login.dto.TokenOutDto;
import com.scheduleplanner.core.mock.AccountServiceFake;
import com.scheduleplanner.core.mock.EncryptFake;
import com.scheduleplanner.core.mock.TokenServiceFake;
import com.scheduleplanner.store.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.scheduleplanner.core.mock.AccountServiceFake.AccounHandlerMethod.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LoginBusinessLogicTest {
    private LoginBusinessLogic loginBusinessLogic;
    private EncryptFake encryptFake;
    private TokenServiceFake tokenServiceFake;
    private AccountServiceFake accountServiceFake;

    private final static String EMAIL = "example@gmail.com";
    private final static String PASSWORD = "password";
    private final static String USERNAME = "USERNAME";
    private final static String TOKEN = "TOKEN";

    @BeforeEach
    void setup() {
        encryptFake = new EncryptFake();
        tokenServiceFake = new TokenServiceFake();
        accountServiceFake = new AccountServiceFake();
        loginBusinessLogic = new LoginBusinessLogic(accountServiceFake, encryptFake, tokenServiceFake);
    }

    @Test
    void emailNotFound(){
        accountServiceFake.callChecker.addMethodCallingValue(FIND_BY_EMAIL, Optional.empty());
        assertThatThrownBy(()->loginBusinessLogic.runService(new LoginAccountInDto(EMAIL, PASSWORD)))
                .isExactlyInstanceOf(EmailAddressNotFound.class)
                .hasMessage("EMAIL_ADDRESS_NOT_FOUND");
    }

    @Test
    void usernameNotFound(){
        accountServiceFake.callChecker.addMethodCallingValue(FIND_BY_USERNAME, Optional.empty());
        assertThatThrownBy(()->loginBusinessLogic.runService(new LoginAccountInDto(USERNAME, PASSWORD)))
                .isExactlyInstanceOf(EmailAddressNotFound.class)
                .hasMessage("EMAIL_ADDRESS_NOT_FOUND");
    }

    @Test
    void wrongPassword(){
        accountServiceFake.callChecker.
                addMethodCallingValue(FIND_BY_EMAIL, Optional.of(new Account().email(EMAIL).passwordHash(PASSWORD)));
        encryptFake.callChecker.addMethodCallingValue(EncryptFake.EncryptMethod.CHECK_PASSWORD,false);
        assertThatThrownBy(()->loginBusinessLogic.runService(new LoginAccountInDto(EMAIL, PASSWORD)))
                .isExactlyInstanceOf(WrongDataException.class);
    }

    @Test
    void positive(){
        accountServiceFake.callChecker.
                addMethodCallingValue(FIND_BY_USERNAME, Optional.of(new Account().email(USERNAME).passwordHash(PASSWORD).isVerified(true)));
        encryptFake.callChecker.addMethodCallingValue(EncryptFake.EncryptMethod.CHECK_PASSWORD,true);
        tokenServiceFake.callChecker.addMethodCallingValue(TokenServiceFake.TokenServiceMethod.GENERATE_TOKEN,TOKEN);
        var result = loginBusinessLogic.runService(new LoginAccountInDto(USERNAME, PASSWORD));
        assertThat(result.token()).isEqualTo(TOKEN);

    }

    @Test
    void accountNotVerified(){
        accountServiceFake.callChecker.
                addMethodCallingValue(FIND_BY_EMAIL, Optional.of(new Account().email(EMAIL).passwordHash(PASSWORD).isVerified(false)));
        encryptFake.callChecker.addMethodCallingValue(EncryptFake.EncryptMethod.CHECK_PASSWORD,true);
        assertThatThrownBy(()->loginBusinessLogic.runService(new LoginAccountInDto(EMAIL, PASSWORD)))
                .isExactlyInstanceOf(UnverifiedAccountException.class)
                .hasMessage("ACCOUNT_NOT_VERIFIED");

    }


}
