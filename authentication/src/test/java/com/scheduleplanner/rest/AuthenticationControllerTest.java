package com.scheduleplanner.rest;

import com.scheduleplanner.core.createaccount.dto.AccountInDto;
import com.scheduleplanner.core.login.dto.TokenOutDto;
import com.scheduleplanner.rest.mock.CreateAccountBusinessLogicFake;
import com.scheduleplanner.rest.mock.LoginBusinessLogicFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationControllerTest {
    private AuthenticationController authenticationController;
    private LoginBusinessLogicFake loginBusinessLogicFake;
    private CreateAccountBusinessLogicFake createAccountBusinessLogicFake;

    @BeforeEach
    void setup(){
        loginBusinessLogicFake = new LoginBusinessLogicFake();
        createAccountBusinessLogicFake = new CreateAccountBusinessLogicFake();
        authenticationController = new AuthenticationController(createAccountBusinessLogicFake, loginBusinessLogicFake);
    }

    @Test void createAccount(){
        var dto = new AccountInDto(null,null,null,null);
        var result = authenticationController.createAccount(dto);
        createAccountBusinessLogicFake.callChecker.checkNextMethod(CreateAccountBusinessLogicFake.RunMethod.RUN_SERVICE,dto);
        assertThat(result.getBody()).isEqualTo("Account created successfully");
    }

    @Test void login(){
        final var token =  new TokenOutDto(null);
        var dto = new com.scheduleplanner.core.login.dto.AccountInDto(null,null);
        loginBusinessLogicFake.callChecker.addMethodCallingValue(LoginBusinessLogicFake.RunMethod.RUN_SERVICE,token);
        var result = authenticationController.loginAccount(dto);
        loginBusinessLogicFake.callChecker.checkNextMethod(LoginBusinessLogicFake.RunMethod.RUN_SERVICE,dto);
        assertThat(result.getBody()).isEqualTo(token);
    }

}
