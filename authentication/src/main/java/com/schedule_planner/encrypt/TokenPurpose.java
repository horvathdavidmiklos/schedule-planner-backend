package com.schedule_planner.encrypt;

public enum TokenPurpose {
    LOGIN,
    RESET_PASSWORD,
    EMAIL_VERIFICATION;


    public String getName() {
        return name().toLowerCase();
    }
}
