package com.schedule_planner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class ValueNotUniqueException extends HandledException {
    public ValueNotUniqueException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
