package com.schedule_planner.encrypt;

public interface TokenService {
    String generateToken(String username,long expirationTime,String purpose );

    Boolean validateToken(String token, String username,String purpose);
}
