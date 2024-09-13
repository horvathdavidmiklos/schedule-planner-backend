package com.scheduleplanner.core.createaccount;

import com.scheduleplanner.core.createaccount.dto.AccountInDto;
import com.scheduleplanner.core.mock.AccountRepositoryFake;
import com.scheduleplanner.core.mock.EncryptFake;
import com.scheduleplanner.common.exception.baseexception.handled.EmptyFieldException;
import com.scheduleplanner.common.exception.baseexception.handled.NotSupportedFormatException;
import com.scheduleplanner.common.exception.baseexception.handled.PasswordNotMatchingException;
import com.scheduleplanner.common.exception.baseexception.handled.ValueNotUniqueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.scheduleplanner.core.mock.AccountRepositoryFake.AccounHandlerMethod.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CreateAccountBusinessLogicTest {
    private static final String TEST_USER_LOWER_CASE = "test_user";
    private static final String TEST_USER = "TEST_USER";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "123456@!abc";
    private static final String TEST_CONFIRM_PASSWORD = "123456@!abc";

    private static final String HASHED_PASSWORD = "HASHED";


    private AccountRepositoryFake accountRepositoryFake;
    private CreateAccountBusinessLogic createAccountBusinessLogic;
    private EncryptFake encryptFake;

    @BeforeEach void setUp() {
        accountRepositoryFake = new AccountRepositoryFake();
        encryptFake = new EncryptFake();
        createAccountBusinessLogic = new CreateAccountBusinessLogic(accountRepositoryFake, encryptFake,null,null);
    }

    @Test void positive() {
        accountRepositoryFake.callChecker.addMethodCallingValue(IS_UNIQUE_EMAIL, true);
        accountRepositoryFake.callChecker.addMethodCallingValue(IS_UNIQUE_USERNAME, true);

        encryptFake.callChecker.addMethodCallingValue(EncryptFake.EncryptMethod.CONVERT_TO_HASH, HASHED_PASSWORD);

        var dto = createDto();
        createAccountBusinessLogic.runService(dto);

        accountRepositoryFake.callChecker.checkNextMethod(IS_UNIQUE_EMAIL, TEST_EMAIL);
        accountRepositoryFake.callChecker.checkNextMethod(IS_UNIQUE_USERNAME, TEST_USER);
        encryptFake.callChecker.checkNextMethod(EncryptFake.EncryptMethod.CONVERT_TO_HASH, TEST_PASSWORD);
        Object[] objects = accountRepositoryFake.callChecker.checkNextMethod(SAVE,
                new NewAccount().email(TEST_EMAIL).username(TEST_USER).passwordHash(HASHED_PASSWORD));
        NewAccount newAccount = (NewAccount) objects[0];
        assertThat(newAccount.createdAt())
                .isBetween(LocalDateTime.now().minusSeconds(1L), LocalDateTime.now().plusSeconds(1L));
    }


    @Test void emailNotUnique() {
        accountRepositoryFake.callChecker.addMethodCallingValue(IS_UNIQUE_EMAIL, false);
        var dto = createDto();
        assertThatThrownBy(() -> createAccountBusinessLogic.runService(dto))
                .isExactlyInstanceOf(ValueNotUniqueException.class);
    }

    @Test void userNameNotUnique() {
        accountRepositoryFake.callChecker.addMethodCallingValue(IS_UNIQUE_EMAIL, true);
        accountRepositoryFake.callChecker.addMethodCallingValue(IS_UNIQUE_USERNAME, false);
        var dto = createDto();
        assertThatThrownBy(() -> createAccountBusinessLogic.runService(dto))
                .isExactlyInstanceOf(ValueNotUniqueException.class);
    }

    @Test void wrongPassword() {
        accountRepositoryFake.callChecker.addMethodCallingValue(IS_UNIQUE_EMAIL, true);
        accountRepositoryFake.callChecker.addMethodCallingValue(IS_UNIQUE_USERNAME, true);
        encryptFake.callChecker.addMethodCallingValue(EncryptFake.EncryptMethod.CONVERT_TO_HASH, HASHED_PASSWORD);
        var dto = new AccountInDto(TEST_USER_LOWER_CASE, TEST_EMAIL, TEST_PASSWORD, "WRONG");
        assertThatThrownBy(() -> createAccountBusinessLogic.runService(dto))
                .isExactlyInstanceOf(PasswordNotMatchingException.class);
    }

    @Test void nullDto() {
        final var dto = new AccountInDto(TEST_USER_LOWER_CASE, TEST_EMAIL, TEST_PASSWORD, null);
        assertThatThrownBy(() -> createAccountBusinessLogic.runService(dto))
                .isExactlyInstanceOf(EmptyFieldException.class);

        assertThatThrownBy(() -> createAccountBusinessLogic.runService(null))
                .isExactlyInstanceOf(EmptyFieldException.class);
    }

    @Test void regex() {
        accountRepositoryFake.callChecker.addMethodCallingValue(IS_UNIQUE_EMAIL,true);
        accountRepositoryFake.callChecker.addMethodCallingValue(IS_UNIQUE_USERNAME,true);
        encryptFake.callChecker.addMethodCallingValue(EncryptFake.EncryptMethod.CONVERT_TO_HASH, HASHED_PASSWORD);
        var dto = new AccountInDto("INVALID SPACE", TEST_EMAIL, TEST_PASSWORD, TEST_CONFIRM_PASSWORD);
        assertThatThrownBy(()->createAccountBusinessLogic.runService(dto))
                .isExactlyInstanceOf(NotSupportedFormatException.class);
    }


    private AccountInDto createDto() {
        return new AccountInDto(TEST_USER_LOWER_CASE, TEST_EMAIL, TEST_PASSWORD, TEST_CONFIRM_PASSWORD);
    }
}
