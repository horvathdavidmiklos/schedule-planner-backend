package com.sheduleplanner.common.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class ValueNotUniqueException extends HandledException {
    public ValueNotUniqueException(String notUniqField) {
        super(notUniqField+"_NOT_UNIQUE:", HttpStatus.BAD_REQUEST);
    }
}
