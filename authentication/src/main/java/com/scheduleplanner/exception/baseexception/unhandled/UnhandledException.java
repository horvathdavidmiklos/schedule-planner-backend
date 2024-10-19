package com.scheduleplanner.exception.baseexception.unhandled;

import org.springframework.http.HttpStatus;

public abstract class UnhandledException extends RuntimeException{
    public final String highlightedMessage;
    public final HttpStatus httpStatus;
    public UnhandledException(Throwable throwable,String message,HttpStatus httpStatus) {
        super(message,throwable);
        highlightedMessage = message;
        this.httpStatus = httpStatus;
    }

    public UnhandledException(String message,HttpStatus httpStatus) {
        super(message);
        highlightedMessage = message;
        this.httpStatus = httpStatus;
    }
}
