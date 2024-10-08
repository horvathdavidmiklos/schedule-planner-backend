package com.scheduleplanner.core.verifiedaccount;

import com.scheduleplanner.common.exception.baseexception.handled.TokenExpiredException;
import com.scheduleplanner.core.mock.AccountServiceFake;
import com.scheduleplanner.core.mock.TokenServiceFake;
import com.scheduleplanner.store.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class VerifyAccountBusinessLogicTest {
    private VerifyAccountBusinessLogic verifyAccountBusinessLogic;
    private AccountServiceFake accountServiceFake;
    private TokenServiceFake tokenServiceFake;


    @BeforeEach
    void setUp() {
        accountServiceFake = new AccountServiceFake();
        tokenServiceFake = new TokenServiceFake();
        verifyAccountBusinessLogic = new VerifyAccountBusinessLogic(accountServiceFake,tokenServiceFake);
    }

    @Test
    void verifyPositive(){
        String url = "[{$}]";
        String username ="USERNAME" ;
        String encode = URLEncoder.encode(url);
        tokenServiceFake.callChecker.addMethodCallingValue(TokenServiceFake.TokenServiceMethod.VALIDATE_TOKEN,true);
        verifyAccountBusinessLogic.runService(encode,username);
        accountServiceFake.callChecker.checkNextMethod(AccountServiceFake.AccounHandlerMethod.VERIFY_ACCOUNT,username);
        tokenServiceFake.callChecker.checkNextMethod(TokenServiceFake.TokenServiceMethod.VALIDATE_TOKEN,url,username);
    }

    @Test
    void expiredToken(){
        String url = "[{$}]";
        String username ="USERNAME" ;
        String encode = URLEncoder.encode(url);
        tokenServiceFake.callChecker.addMethodCallingValue(TokenServiceFake.TokenServiceMethod.VALIDATE_TOKEN,false);
        assertThatThrownBy(()->verifyAccountBusinessLogic.runService(encode,username))
                .isExactlyInstanceOf(TokenExpiredException.class)
                .hasMessage("TOKEN_EXPIRED");
    }
}