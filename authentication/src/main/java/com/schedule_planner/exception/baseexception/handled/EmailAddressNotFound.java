package com.schedule_planner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class EmailAddressNotFound extends HandledException {
    public EmailAddressNotFound(String messagge) {
        super(messagge, HttpStatus.NOT_FOUND);
    }
}
