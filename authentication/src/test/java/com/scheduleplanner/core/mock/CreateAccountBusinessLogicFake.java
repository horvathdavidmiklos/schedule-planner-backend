package com.scheduleplanner.core.mock;

import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.core.createaccount.dto.CreateAccountInDto;

public class CreateAccountBusinessLogicFake extends CreateAccountBusinessLogic {
    public CreateAccountBusinessLogicFake() {
        super(null,null,null,null);
    }
    public CreateAccountInDto inputDto;

    @Override
    public void runService(CreateAccountInDto accountDto) {
        inputDto = accountDto;
    }
}
