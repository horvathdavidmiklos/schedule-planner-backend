package com.scheduleplanner.core.mock;


import com.scheduleplanner.secret.TokenService;
import mockhelper.CallChecker;

public class TokenServiceFake implements TokenService {
    public enum TokenServiceMethod {
        VALIDATE_TOKEN,
        GENERATE_TOKEN,
    }

    public CallChecker<TokenServiceMethod> callChecker;

    public TokenServiceFake() {
        callChecker = new CallChecker<>();
    }

    @Override
    public Boolean validateToken(String token, String username) {
        return (Boolean) callChecker.addCall(TokenServiceMethod.VALIDATE_TOKEN, token, username);
    }

    @Override
    public String generateToken(String username,long expirationTime) {
        return (String) callChecker.addCall(TokenServiceMethod.GENERATE_TOKEN, username,expirationTime);
    }
}
