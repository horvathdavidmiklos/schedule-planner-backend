package com.scheduleplanner.rest.mock;

import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.core.login.LoginBusinessLogic;
import com.scheduleplanner.core.login.dto.AccountInDto;
import com.scheduleplanner.core.login.dto.TokenOutDto;
import mockhelper.CallChecker;

public class LoginBusinessLogicFake extends LoginBusinessLogic {
    public enum RunMethod{
        RUN_SERVICE
    }
    public CallChecker<RunMethod> callChecker;
    public LoginBusinessLogicFake() {
        super(null,null,null);
        callChecker = new CallChecker<>();
    }

    @Override
    public TokenOutDto runService(AccountInDto dto) {
        return (TokenOutDto) callChecker.addCall(RunMethod.RUN_SERVICE,dto);
    }
}
