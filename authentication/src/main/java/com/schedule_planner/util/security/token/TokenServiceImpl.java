package com.schedule_planner.util.security.token;

import com.schedule_planner.exception.baseexception.handled.TokenException;
import com.schedule_planner.rest.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TokenServiceImpl implements TokenService {

    private final SecretKey SECRET_KEY;

    public TokenServiceImpl(final JwtProperties jwtProperties) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecretKey()));
    }

    @Override
    public String generateToken(String username, TokenExpirationTime expirationTime,TokenPurpose purpose) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("purpose", purpose);
        return createToken(claims, username, expirationTime.getTime());
    }

    @Override
    public Boolean validateToken(String token) {
        return !(isTokenExpired(token));
    }

    @Override
    public String extractPurpose(String token) {
        return extractClaim(token, claims -> claims.get("purpose", String.class));
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTimeInMs){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMs))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new TokenException("INVALID_OR_EXPIRED_TOKEN_EXCEPTION");
        }
    }

    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
