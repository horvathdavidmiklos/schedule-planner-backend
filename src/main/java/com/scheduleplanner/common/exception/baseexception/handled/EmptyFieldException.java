package com.scheduleplanner.common.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class EmptyFieldException extends HandledException {
    public EmptyFieldException() {
        super("NULL_VALUE",HttpStatus.BAD_REQUEST);
    }
}
