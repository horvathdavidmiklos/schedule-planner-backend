package com.scheduleplanner.core.mock;

import com.scheduleplanner.store.Account;
import com.scheduleplanner.store.AccountService;
import mockhelper.CallChecker;

import java.util.Optional;


public class AccountServiceFake implements AccountService {
    public enum AccounHandlerMethod {
        SAVE,
        FIND_BY_EMAIL,
        FIND_BY_USERNAME,
        VERIFY_ACCOUNT
    }

    public Account resultAccount;

    public CallChecker<AccounHandlerMethod> callChecker;

    public AccountServiceFake() {
        callChecker = new CallChecker<>();
    }

    @Override
    public void save(Account account) {
        resultAccount = account;
        callChecker.addCall(AccounHandlerMethod.SAVE, account);
    }

    @Override
    public void verifyAccount(String username) {
        callChecker.addCall(AccounHandlerMethod.VERIFY_ACCOUNT, username);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return (Optional<Account>) callChecker.addCall(AccounHandlerMethod.FIND_BY_USERNAME, username);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return (Optional<Account>) callChecker.addCall(AccounHandlerMethod.FIND_BY_EMAIL, email);
    }
}
