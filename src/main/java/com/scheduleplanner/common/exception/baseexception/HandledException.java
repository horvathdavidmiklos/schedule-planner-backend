package com.scheduleplanner.common.exception.baseexception;

import org.springframework.http.HttpStatus;

public class HandledException extends RuntimeException  {
    public final String highlightedMessage;
    public final HttpStatus httpStatus;
    public HandledException(String message, HttpStatus httpStatus) {
        super();
        highlightedMessage = message;
        this.httpStatus = httpStatus;
    }
}
