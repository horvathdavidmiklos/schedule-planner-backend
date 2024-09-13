package com.scheduleplanner.common.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends HandledException{
    public TokenExpiredException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
