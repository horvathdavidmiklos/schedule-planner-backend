package com.schedule_planner.gateway.email;

public interface EmailConnector {
    void send(EmailData email);
}
