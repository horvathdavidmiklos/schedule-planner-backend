package com.scheduleplanner.dataaccesslayer.exception;

import com.scheduleplanner.common.exception.baseexception.UnhandledException;

public class DatabaseNotAvailableException extends UnhandledException {
    public DatabaseNotAvailableException(Throwable cause) {
        super(cause,"DATABASE_NOT_AVAILABLE");
    }
}
