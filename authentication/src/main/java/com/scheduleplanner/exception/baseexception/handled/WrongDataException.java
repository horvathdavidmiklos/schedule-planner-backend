package com.scheduleplanner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class WrongDataException extends HandledException {
    public WrongDataException() {
        super("USERNAME_OR_PASSWORD_NOT_VALID", HttpStatus.BAD_REQUEST);
    }
}
