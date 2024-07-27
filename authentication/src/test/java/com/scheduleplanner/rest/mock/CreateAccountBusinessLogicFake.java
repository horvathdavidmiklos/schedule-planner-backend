package com.scheduleplanner.rest.mock;

import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.core.createaccount.dto.AccountInDto;
import mockhelper.CallChecker;

public class CreateAccountBusinessLogicFake extends CreateAccountBusinessLogic {
    public enum RunMethod{
        RUN_SERVICE
    }
    public CallChecker<RunMethod> callChecker;
    public CreateAccountBusinessLogicFake() {
        super(null,null);
        callChecker = new CallChecker<>();
    }

    @Override
    public void runService(AccountInDto dto) {
        callChecker.addCall(RunMethod.RUN_SERVICE,dto);
    }
}
