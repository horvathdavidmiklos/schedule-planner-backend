package com.scheduleplanner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class TokenException extends HandledException{
    public TokenException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
