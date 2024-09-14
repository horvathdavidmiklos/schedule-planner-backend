package com.scheduleplanner.core.createaccount;

public interface SendVerificationEmail {
    void send(String username, String emailAddress, String verificationLink);
}
