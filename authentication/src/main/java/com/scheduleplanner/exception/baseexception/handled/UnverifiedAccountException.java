package com.scheduleplanner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class UnverifiedAccountException extends HandledException {
    public UnverifiedAccountException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
