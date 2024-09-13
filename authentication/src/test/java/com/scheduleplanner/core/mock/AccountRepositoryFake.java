package com.scheduleplanner.core.mock;

import com.scheduleplanner.gateway.store.AccountRepository;
import mockhelper.CallChecker;


public class AccountRepositoryFake implements AccountRepository {
    public enum AccounHandlerMethod {
        SAVE,
        IS_UNIQUE_USERNAME,
        IS_UNIQUE_EMAIL,
        FIND_BY_EMAIL,
        FIND_BY_USERNAME,
    }

    public CallChecker<AccounHandlerMethod> callChecker;

    public AccountRepositoryFake() {
        callChecker = new CallChecker<>();
    }

    @Override
    public void saveUnverified(NewAccount account) {
        callChecker.addCall(AccounHandlerMethod.SAVE, account);
    }

    @Override
    public boolean isUniqueUsername(String username) {
        return (boolean) callChecker.addCall(AccounHandlerMethod.IS_UNIQUE_USERNAME, username);
    }

    @Override
    public boolean isUniqueEmail(String email) {
        return (boolean) callChecker.addCall(AccounHandlerMethod.IS_UNIQUE_EMAIL, email);
    }

    @Override
    public Account findByEmail(String email) {
        return (Account) callChecker.addCall(AccounHandlerMethod.FIND_BY_EMAIL, email);

    }

    @Override
    public Account findByUsername(String username) {
        return (Account) callChecker.addCall(AccounHandlerMethod.FIND_BY_USERNAME, username);
    }
}
