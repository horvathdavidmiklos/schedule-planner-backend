package com.scheduleplanner.common.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public abstract class HandledException extends RuntimeException  {
    public final String highlightedMessage;
    public final HttpStatus httpStatus;
    public HandledException(String message, HttpStatus httpStatus) {
        super();
        highlightedMessage = message;
        this.httpStatus = httpStatus;
    }
}
