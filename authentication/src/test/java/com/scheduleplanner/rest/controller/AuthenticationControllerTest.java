package com.scheduleplanner.rest.controller;

import com.scheduleplanner.core.createaccount.dto.CreateAccountInDto;
import com.scheduleplanner.core.login.dto.LoginAccountInDto;
import com.scheduleplanner.core.login.dto.TokenOutDto;
import com.scheduleplanner.core.mock.CreateAccountBusinessLogicFake;
import com.scheduleplanner.core.mock.LoginBusinessLogicFake;
import com.scheduleplanner.core.mock.VerifyAccountBusinessLogicFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthenticationControllerTest extends AuthenticationController{
    boolean isCalledHandledException;
    public Object responseObject;

    private static final CreateAccountBusinessLogicFake createAccountBusinessLogicFake = new CreateAccountBusinessLogicFake();
    private static final LoginBusinessLogicFake loginBusinessLogicFake = new LoginBusinessLogicFake();
    private static final  VerifyAccountBusinessLogicFake verifyAccountBusinessLogicFake = new VerifyAccountBusinessLogicFake();

    public AuthenticationControllerTest() {
        super(createAccountBusinessLogicFake, loginBusinessLogicFake, verifyAccountBusinessLogicFake);
        isCalledHandledException = false;
    }

    @Test
    void createAccount() {
        var response = createAccount(new CreateAccountInDto("USERNAME",null,null,null));
        assertThat(response.getBody()).isEqualTo("Account created successfully");
        assertThat(createAccountBusinessLogicFake.inputDto.username()).isEqualTo("USERNAME");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(isCalledHandledException).isTrue();
    }

    @Test
    void login() {
        var response = loginAccount(new LoginAccountInDto("ID",null));
        assertThat(isCalledHandledException).isTrue();
        assertThat(loginBusinessLogicFake.inDto.id()).isEqualTo("ID");
        assertThat(((TokenOutDto)responseObject).token()).isEqualTo("TOKEN");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    void verifyAccount() {
        var response = verifyEmail("TOKEN","USERNAME");
        assertThat(response.getBody()).isEqualTo("Email verification successful");
        assertThat(isCalledHandledException).isTrue();
        assertThat(verifyAccountBusinessLogicFake.urlEncodeTokenInput).isEqualTo("TOKEN");
        assertThat(verifyAccountBusinessLogicFake.userNameInput).isEqualTo("USERNAME");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Override
    protected ResponseEntity<String> handledException(Runnable runnable,ResponseEntity<String> response) {
        isCalledHandledException = true;
        runnable.run();
        return response;
    }

    @Override
    protected <T> ResponseEntity<?> handledException(Supplier<T> supplier) {
        isCalledHandledException = true;
        responseObject =  supplier.get();
        return super.handledException(supplier);
    }
}