package com.scheduleplanner.authorization.createaccount.exception;

import com.scheduleplanner.common.exception.baseexception.HandledException;
import org.springframework.http.HttpStatus;

public class ValueNotUniqueException extends HandledException {
    public ValueNotUniqueException(String notUniqField) {
        super(notUniqField+"_NOT_UNIQUE:", HttpStatus.BAD_REQUEST);
    }
}
