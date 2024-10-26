package com.schedule_planner.util.security.authorization;

import com.schedule_planner.util.security.token.TokenPurpose;
import com.schedule_planner.util.security.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthorizationAspect {

    private final TokenService tokenService;

    public AuthorizationAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Around("@annotation(authorized)")
    public Object checkAuthorization(ProceedingJoinPoint joinPoint, Authorized authorized) throws Throwable {
        String token = extractTokenFromHeader();
        if (token != null) {
            String username = tokenService.extractUsername(token);
            String purpose = tokenService.extractPurpose(token);
            if (tokenService.validateToken(token) && purpose.equalsIgnoreCase(TokenPurpose.LOGIN.getName())) {
                return joinPoint.proceed(new Object[]{username});
            }
        }
        throw new SecurityException("Unauthorized access");
    }

    private String extractTokenFromHeader() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authHeader != null ? authHeader.replace("Bearer ", "").trim() : null;
    }
}


