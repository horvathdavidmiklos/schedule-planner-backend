package com.sheduleplanner.common.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class NotSupportedFormatException extends HandledException {
    public NotSupportedFormatException(String wrongField) {
        super(wrongField+"NOT_SUPPORTED_SYNTAX", HttpStatus.BAD_REQUEST);
    }
}
