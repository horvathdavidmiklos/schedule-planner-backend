package com.scheduleplanner.core.createaccount;

import com.scheduleplanner.core.createaccount.dto.CreateAccountInDto;
import com.scheduleplanner.core.mock.AccountServiceFake;
import com.scheduleplanner.core.mock.EncryptFake;
import com.scheduleplanner.common.exception.baseexception.handled.EmptyFieldException;
import com.scheduleplanner.common.exception.baseexception.handled.NotSupportedFormatException;
import com.scheduleplanner.common.exception.baseexception.handled.PasswordNotMatchingException;
import com.scheduleplanner.common.exception.baseexception.handled.ValueNotUniqueException;
import com.scheduleplanner.core.mock.SendVerificationEmailFake;
import com.scheduleplanner.core.mock.TokenServiceFake;
import com.scheduleplanner.store.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.scheduleplanner.core.mock.EncryptFake.EncryptMethod.CONVERT_TO_HASH;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CreateAccountBusinessLogicTest {
    private static final String TEST_USER_LOWER_CASE = "test_user";
    private static final String TEST_USER = "TEST_USER";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "123456@!abc";
    private static final String TOKEN = "TOKEN%24-%7B";
    private static final String HASHED_PASSWORD = "HASHED";
    private static final String TOKEN_ENCODE = "TOKEN$-{";



    private AccountServiceFake accountServiceFake;
    private SendVerificationEmailFake sendVerificationEmailFake;
    private CreateAccountBusinessLogic createAccountBusinessLogic;
    private EncryptFake encryptFake;
    private TokenServiceFake tokenServiceFake;

    @BeforeEach void setUp() {
        accountServiceFake = new AccountServiceFake();
        encryptFake = new EncryptFake();
        sendVerificationEmailFake = new SendVerificationEmailFake();
        tokenServiceFake = new TokenServiceFake();
        createAccountBusinessLogic = new CreateAccountBusinessLogic(accountServiceFake,encryptFake ,sendVerificationEmailFake,tokenServiceFake);
    }

    @Test
    void emptyField() {
        assertThatThrownBy(()->createAccountBusinessLogic.runService(new CreateAccountInDto(TEST_USER,null,null,null)))
                .isExactlyInstanceOf(EmptyFieldException.class);
    }

    @Test
    void notMatchingRegexEmail(){
        assertThatThrownBy(()->createAccountBusinessLogic.runService(new CreateAccountInDto(TEST_USER,"WRONG",TEST_PASSWORD,TEST_PASSWORD)))
                .isExactlyInstanceOf(NotSupportedFormatException.class)
                .hasMessage("EMAIL_WRONG_SYNTAX");
    }

    @Test
    void notMatchingRegexUsername(){
        assertThatThrownBy(()->createAccountBusinessLogic.runService(new CreateAccountInDto("WRONG@",TEST_EMAIL,TEST_PASSWORD,TEST_PASSWORD)))
                .isExactlyInstanceOf(NotSupportedFormatException.class)
                .hasMessage("USERNAME_WRONG_SYNTAX");
    }

    @Test
    void emailIsAlreadyTaken(){
        accountServiceFake.callChecker.addMethodCallingValue(AccountServiceFake.AccounHandlerMethod.FIND_BY_EMAIL,Optional.of(new Account().isVerified(true).email(TEST_EMAIL)));
        assertThatThrownBy(()->createAccountBusinessLogic.runService(new CreateAccountInDto(TEST_USER,TEST_EMAIL,TEST_PASSWORD,TEST_PASSWORD)))
                .isExactlyInstanceOf(ValueNotUniqueException.class)
                .hasMessage("EMAIL_IS_ALREADY_TAKEN");
    }

    @Test
    void emailIsAlreadyTakenAndEmailExists(){
        accountServiceFake.callChecker.addMethodCallingValue(AccountServiceFake.AccounHandlerMethod.FIND_BY_EMAIL,Optional.of(new Account().isVerified(false).email(TEST_EMAIL).username(TEST_USER)));
        accountServiceFake.callChecker.addMethodCallingValue(AccountServiceFake.AccounHandlerMethod.FIND_BY_USERNAME,Optional.of(new Account().isVerified(false).email(TEST_EMAIL).username(TEST_USER)));
        tokenServiceFake.callChecker.addMethodCallingValue(TokenServiceFake.TokenServiceMethod.GENERATE_TOKEN,"TOKEN");
        assertThatCode(()->createAccountBusinessLogic.runService(new CreateAccountInDto(TEST_USER,TEST_EMAIL,TEST_PASSWORD,TEST_PASSWORD)))
                .doesNotThrowAnyException();
    }

    @Test
    void usernameIsAlreadyTaken(){
        accountServiceFake.callChecker.addMethodCallingValue(AccountServiceFake.AccounHandlerMethod.FIND_BY_EMAIL,Optional.empty());
        accountServiceFake.callChecker.addMethodCallingValue(AccountServiceFake.AccounHandlerMethod.FIND_BY_USERNAME,Optional.of(new Account().email(TEST_EMAIL).username(TEST_USER)));
        assertThatThrownBy(()->createAccountBusinessLogic.runService(new CreateAccountInDto(TEST_USER,TEST_EMAIL,TEST_PASSWORD,TEST_PASSWORD)))
                .isExactlyInstanceOf(ValueNotUniqueException.class)
                .hasMessage("USERNAME_IS_ALREADY_TAKEN");
    }

    @Test
    void passwordUnMatch(){
        accountServiceFake.callChecker.addMethodCallingValue(AccountServiceFake.AccounHandlerMethod.FIND_BY_EMAIL,Optional.empty());
        accountServiceFake.callChecker.addMethodCallingValue(AccountServiceFake.AccounHandlerMethod.FIND_BY_USERNAME,Optional.empty());
        assertThatThrownBy(()->createAccountBusinessLogic.runService(new CreateAccountInDto(TEST_USER,TEST_EMAIL,TEST_PASSWORD,"PASSWORD_WRONG")))
                .isExactlyInstanceOf(PasswordNotMatchingException.class)
                .hasMessage("PASSWORD_NOT_MATCHING");
    }

    @Test
    void positive(){
        accountServiceFake.callChecker.addMethodCallingValue(AccountServiceFake.AccounHandlerMethod.FIND_BY_EMAIL,Optional.empty());
        accountServiceFake.callChecker.addMethodCallingValue(AccountServiceFake.AccounHandlerMethod.FIND_BY_USERNAME,Optional.empty());
        tokenServiceFake.callChecker.addMethodCallingValue(TokenServiceFake.TokenServiceMethod.GENERATE_TOKEN,TOKEN_ENCODE);
        encryptFake.callChecker.addMethodCallingValue(CONVERT_TO_HASH,HASHED_PASSWORD);

        assertThatCode(()->createAccountBusinessLogic.runService(new CreateAccountInDto(TEST_USER,TEST_EMAIL,TEST_PASSWORD,TEST_PASSWORD)))
                .doesNotThrowAnyException();
        encryptFake.callChecker.checkNextMethod(CONVERT_TO_HASH,TEST_PASSWORD);
        sendVerificationEmailFake.callChecker.checkNextMethod(SendVerificationEmailFake.Method.SEND,TEST_USER,TEST_EMAIL,TOKEN);
        assertThat(accountServiceFake.resultAccount.username()).isEqualTo(TEST_USER.toLowerCase());
        assertThat(accountServiceFake.resultAccount.passwordHash()).isEqualTo(HASHED_PASSWORD);
    }
}
