package com.schedule_planner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class EncryptException extends HandledException{
    public EncryptException() {
        super("ENCRYPT_PASSWORD_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
