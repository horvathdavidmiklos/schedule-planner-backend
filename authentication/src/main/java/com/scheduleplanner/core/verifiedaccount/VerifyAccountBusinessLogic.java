package com.scheduleplanner.core.verifiedaccount;

import com.scheduleplanner.common.exception.baseexception.handled.TokenExpiredException;
import com.scheduleplanner.encrypt.TokenService;
import com.scheduleplanner.store.AccountService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class VerifyAccountBusinessLogic {
    private final AccountService accountService;
    private final TokenService tokenService;

    public VerifyAccountBusinessLogic(AccountService accountService, TokenService tokenService) {
        this.accountService = accountService;
        this.tokenService = tokenService;
    }


    public void runService(String urlEncodedToken,String userName) {
        var token = URLDecoder.decode(urlEncodedToken, StandardCharsets.UTF_8);
        if(tokenService.validateToken(token,userName)){
            accountService.verifyAccount(userName);
        }else{
            throw new TokenExpiredException("TOKEN_EXPIRED");
        }
    }
}
