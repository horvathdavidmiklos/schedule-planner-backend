package com.scheduleplanner.store;

import com.scheduleplanner.common.exception.baseexception.unhandled.UnknownException;
import com.scheduleplanner.core.mock.AccountRepositoryFake;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class AccountServiceImplTest {
    private AccountService accountService;
    private AccountRepositoryFake accountRepositoryFake;
    private final static Account TEST_ACCOUNT = new Account().email("test@email.com");

    @BeforeEach
    void setUp() {
        accountRepositoryFake = new AccountRepositoryFake();
        accountService = new AccountServiceImpl(accountRepositoryFake);
    }

    @Test
    void findByEmail(){
        var result = Optional.of(TEST_ACCOUNT);
        accountRepositoryFake.findByEmailResult = result;
        assertThat(accountService.findByEmail(null)).isEqualTo(result);
    }

    @Test
    void findByUsername(){
        var result = Optional.of(TEST_ACCOUNT);
        accountRepositoryFake.findByUsernameResult = result;
        assertThat(accountService.findByUsername(null)).isEqualTo(result);
    }

    @Test
    void save(){
        accountService.save(TEST_ACCOUNT);
        assertThat(accountRepositoryFake.saveIsCalled).isTrue();
    }

    @Test
    void verify(){
        accountRepositoryFake.findByUsernameResult = Optional.of(TEST_ACCOUNT);
        accountService.verifyAccount(null);
        assertThat(accountRepositoryFake.saveIsCalled).isTrue();
        assertThat(accountRepositoryFake.inputAccount.isVerified()).isTrue();
    }

    @Test
    void accountNotExists(){
        accountRepositoryFake.findByUsernameResult = Optional.empty();
        assertThatCode(()->accountService.verifyAccount(null))
                .isExactlyInstanceOf(UnknownException.class)
                .hasMessage("ACCOUNT_NOT_FOUND");

    }

}
