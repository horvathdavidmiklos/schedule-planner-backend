package com.scheduleplanner.common.gateway.email;

public interface EmailConnector {
    void send(EmailData email);
}
