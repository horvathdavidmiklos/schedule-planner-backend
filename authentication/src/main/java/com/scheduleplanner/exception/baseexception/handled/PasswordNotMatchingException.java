package com.scheduleplanner.exception.baseexception.handled;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchingException extends HandledException {
    public PasswordNotMatchingException() {
        super("PASSWORD_NOT_MATCHING", HttpStatus.CONFLICT);
    }
}
