package com.schedule_planner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public abstract class HandledException extends RuntimeException  {
    public final HttpStatus httpStatus;
    public HandledException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
