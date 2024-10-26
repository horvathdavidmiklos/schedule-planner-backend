package com.schedule_planner.util.security.token;

public enum TokenExpirationTime {
    ONE_YEAR(1000L * 60 * 60 * 24 * 365),
    ONE_DAY(1000L * 60 * 60 * 24);

    private final Long expirationTime;

    TokenExpirationTime(Long expirationTime){
        this.expirationTime = expirationTime;
    }
    public Long getTime(){
        return expirationTime;
    }
}
