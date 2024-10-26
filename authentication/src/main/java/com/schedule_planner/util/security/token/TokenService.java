package com.schedule_planner.util.security.token;

public interface TokenService {
    String generateToken(String username,TokenExpirationTime expirationTime,TokenPurpose tokenPurpose);

    Boolean validateToken(String token);
    String extractPurpose(String token);
    String extractUsername(String token);
}