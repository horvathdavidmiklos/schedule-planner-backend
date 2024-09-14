//package com.scheduleplanner.core.login;
//
//import com.scheduleplanner.core.login.dto.AccountInDto;
//import com.scheduleplanner.core.login.dto.TokenOutDto;
//import com.scheduleplanner.core.mock.accountRepositoryFake;
//import com.scheduleplanner.core.mock.EncryptFake;
//import com.scheduleplanner.core.mock.TokenServiceFake;
//import com.scheduleplanner.common.exception.baseexception.handled.WrongDataException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static com.scheduleplanner.core.mock.accountRepositoryFake.AccounHandlerMethod.FIND_BY_EMAIL;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//public class LoginBusinessLogicTest {
//    private LoginBusinessLogic loginBusinessLogic;
//    private EncryptFake encryptFake;
//    private TokenServiceFake tokenServiceFake;
//    private accountRepositoryFake accountRepositoryFake;
//
//    private final static String EMAIL = "example@gmail.com";
//    private final static String PASSWORD = "password";
//    private final static String USERNAME = "USERNAME";
//    private final static String TOKEN = "TOKEN";
//
//    @BeforeEach
//    void setup() {
//        encryptFake = new EncryptFake();
//        tokenServiceFake = new TokenServiceFake();
//        accountRepositoryFake = new accountRepositoryFake();
//        loginBusinessLogic = new LoginBusinessLogic(accountRepositoryFake, encryptFake, tokenServiceFake);
//    }
//
//    @Test
//    void positiveWithEmail() {
//        var dto = new AccountInDto(EMAIL, PASSWORD);
//        var token = new TokenOutDto(TOKEN);
//        accountRepositoryFake.callChecker.addMethodCallingValue(FIND_BY_EMAIL,new Account().username(USERNAME));
//        encryptFake.callChecker.addMethodCallingValue(EncryptFake.EncryptMethod.CHECK_PASSWORD,true);
//        tokenServiceFake.callChecker.addMethodCallingValue(TokenServiceFake.TokenServiceMethod.GENERATE_TOKEN,token.token());
//        var result = loginBusinessLogic.runService(dto);
//        assertThat(result).isEqualTo(token);
//    }
//
//    @Test
//    void positiveWithUsername() {
//        var dto = new AccountInDto(USERNAME, PASSWORD);
//        var token = new TokenOutDto(TOKEN);
//        accountRepositoryFake.callChecker.addMethodCallingValue(com.scheduleplanner.core.mock.accountRepositoryFake.AccounHandlerMethod.FIND_BY_USERNAME,new Account().username(USERNAME));
//        encryptFake.callChecker.addMethodCallingValue(EncryptFake.EncryptMethod.CHECK_PASSWORD,true);
//        tokenServiceFake.callChecker.addMethodCallingValue(TokenServiceFake.TokenServiceMethod.GENERATE_TOKEN,token.token());
//        var result = loginBusinessLogic.runService(dto);
//        assertThat(result).isEqualTo(token);
//    }
//
//    @Test
//    void wrongPassword() {
//        var dto = new AccountInDto(USERNAME, PASSWORD);
//        var token = new TokenOutDto(TOKEN);
//        accountRepositoryFake.callChecker.addMethodCallingValue(com.scheduleplanner.core.mock.accountRepositoryFake.AccounHandlerMethod.FIND_BY_USERNAME, new Account().username(USERNAME));
//        encryptFake.callChecker.addMethodCallingValue(EncryptFake.EncryptMethod.CHECK_PASSWORD,false);
//        tokenServiceFake.callChecker.addMethodCallingValue(TokenServiceFake.TokenServiceMethod.GENERATE_TOKEN,token.token());
//        assertThatThrownBy(()->loginBusinessLogic.runService(dto))
//                .isExactlyInstanceOf(WrongDataException.class);
//    }
//
//    @Test
//    void accountNotExists() {
//        var dto = new AccountInDto(USERNAME, PASSWORD);
//        assertThatThrownBy(()->loginBusinessLogic.runService(dto))
//                .isExactlyInstanceOf(WrongDataException.class);
//    }
//
//}
