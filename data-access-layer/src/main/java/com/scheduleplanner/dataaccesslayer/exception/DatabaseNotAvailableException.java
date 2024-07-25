package com.scheduleplanner.dataaccesslayer.exception;


import com.sheduleplanner.common.exception.baseexception.unhandled.UnhandledException;

public class DatabaseNotAvailableException extends UnhandledException {
    public DatabaseNotAvailableException(Throwable cause) {
        super(cause,"DATABASE_NOT_AVAILABLE");
    }
}
