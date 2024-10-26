package com.schedule_planner.core.create_account;

public interface SendVerificationEmail {
    void send(String username, String emailAddress, String token);
}
