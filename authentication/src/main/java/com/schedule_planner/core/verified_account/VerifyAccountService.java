package com.schedule_planner.core.verified_account;

import com.schedule_planner.exception.baseexception.handled.TokenException;
import com.schedule_planner.log.SensitiveData;
import com.schedule_planner.store.AccountService;
import com.schedule_planner.util.security.token.TokenService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class VerifyAccountService {
    private final AccountService accountService;
    private final TokenService tokenService;

    public VerifyAccountService(AccountService accountService, TokenService tokenService) {
        this.accountService = accountService;
        this.tokenService = tokenService;
    }


    public void runService(@SensitiveData String urlEncodedToken, String userName, String purpose) {
        var token = URLDecoder.decode(urlEncodedToken, StandardCharsets.UTF_8);
        if(tokenService.validateToken(token)
                && tokenService.extractPurpose(purpose).equals(purpose)
                && tokenService.extractUsername(userName).equals(userName)) {
            accountService.verifyAccount(userName);
        }else{
            throw new TokenException("INVALID_OR_EXPIRED_TOKEN_EXCEPTION");
        }
    }
}
