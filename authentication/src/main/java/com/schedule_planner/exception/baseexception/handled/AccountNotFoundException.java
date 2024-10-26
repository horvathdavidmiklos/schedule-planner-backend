package com.schedule_planner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends HandledException {
    public AccountNotFoundException() {
        super("ACCOUNT_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
