package com.scheduleplanner.authorization.createaccount.exception;

import com.scheduleplanner.common.exception.baseexception.HandledException;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchingException extends HandledException {
    public PasswordNotMatchingException() {
        super("PASSWORD_NOT_MATCHING", HttpStatus.CONFLICT);
    }
}
