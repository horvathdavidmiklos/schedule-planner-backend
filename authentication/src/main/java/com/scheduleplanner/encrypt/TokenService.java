package com.scheduleplanner.encrypt;

public interface TokenService {
    String generateToken(String username,long expirationTime );

    Boolean validateToken(String token, String username);
}
