package com.sheduleplanner.common.exception.baseexception.unhandled;


public class DatabaseNotAvailableException extends UnhandledException {
    public DatabaseNotAvailableException(Throwable cause) {
        super(cause,"DATABASE_NOT_AVAILABLE");
    }
}
