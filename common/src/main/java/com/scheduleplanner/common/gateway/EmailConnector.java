package com.scheduleplanner.common.gateway;

import com.scheduleplanner.common.gateway.email.EmailData;

public interface EmailConnector {
    void send(EmailData email);
}
