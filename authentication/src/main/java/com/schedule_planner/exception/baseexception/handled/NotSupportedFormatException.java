package com.schedule_planner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class NotSupportedFormatException extends HandledException {
    public NotSupportedFormatException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
