package com.scheduleplanner.common.exception.baseexception.unhandled;

import org.springframework.http.HttpStatus;

public class UnknownException extends UnhandledException {
    public UnknownException( String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
