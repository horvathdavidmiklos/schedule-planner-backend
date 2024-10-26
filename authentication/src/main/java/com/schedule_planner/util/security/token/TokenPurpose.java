package com.schedule_planner.util.security.token;

public enum TokenPurpose {
    LOGIN,
    RESET_PASSWORD,
    EMAIL_VERIFICATION;


    public String getName() {
        return name().toLowerCase();
    }
}
