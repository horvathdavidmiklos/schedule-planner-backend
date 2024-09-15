package com.scheduleplanner.encrypt;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {
    private static final String USER_NAME = "USER_NAME";
    private TokenService tokenService;
    @BeforeEach
    void setup(){
        tokenService = new TokenServiceImpl();
    }

    @Test void positive(){
        var token = tokenService.generateToken(USER_NAME,1000);
        assertThat(tokenService.validateToken(token,USER_NAME)).isTrue();
    }

    @Test void wrongUsername(){
        var token = tokenService.generateToken(USER_NAME,1000);
        assertThat(tokenService.validateToken(token,USER_NAME+"WRONG")).isFalse();
    }

    @Test void tokenExpired() throws InterruptedException {
        var token = tokenService.generateToken(USER_NAME,1);
        Thread.sleep(100000);
        assertThat(tokenService.validateToken(token,USER_NAME)).isFalse();
    }

    @Test
    void expired() {
        long expirationTime = -1000 * 60;
        var token = tokenService.generateToken(USER_NAME, expirationTime);
        try {
            tokenService.validateToken(token, USER_NAME);
            fail("Expected an ExpiredJwtException to be thrown");
        } catch (ExpiredJwtException e) {
            assertThat(e.getMessage()).contains("JWT expired");
        }
    }
}