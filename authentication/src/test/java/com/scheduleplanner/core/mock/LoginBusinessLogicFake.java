package com.scheduleplanner.core.mock;

import com.scheduleplanner.core.login.LoginBusinessLogic;
import com.scheduleplanner.core.login.dto.LoginAccountInDto;
import com.scheduleplanner.core.login.dto.TokenOutDto;

public class LoginBusinessLogicFake extends LoginBusinessLogic {
    public LoginAccountInDto inDto;

    public LoginBusinessLogicFake() {
        super(null, null, null);
    }

    @Override
    public TokenOutDto runService(LoginAccountInDto dto) {
        inDto = dto;
        return new TokenOutDto("TOKEN");
    }
}
