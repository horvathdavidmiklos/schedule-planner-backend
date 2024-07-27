package com.scheduleplanner.core.mock;

import com.scheduleplanner.gateway.store.AccountHandler;
import com.scheduleplanner.common.entity.Account;
import com.scheduleplanner.common.entity.NewAccount;
import mockhelper.CallChecker;


public class AccountHandlerFake implements AccountHandler {
    public enum AccounHandlerMethod {
        SAVE,
        IS_UNIQUE_USERNAME,
        IS_UNIQUE_EMAIL,
        FIND_BY_EMAIL,
        FIND_BY_USERNAME,
    }

    public CallChecker<AccounHandlerMethod> callChecker;

    public AccountHandlerFake() {
        callChecker = new CallChecker<>();
    }

    @Override
    public void save(NewAccount account) {
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
