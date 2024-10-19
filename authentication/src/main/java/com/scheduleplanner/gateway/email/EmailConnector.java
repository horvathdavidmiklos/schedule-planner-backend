package com.scheduleplanner.gateway.email;

public interface EmailConnector {
    void send(EmailData email);
}
