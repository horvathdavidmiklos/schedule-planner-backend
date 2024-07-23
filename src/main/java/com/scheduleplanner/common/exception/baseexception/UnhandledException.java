package com.scheduleplanner.common.exception.baseexception;

public class UnhandledException extends RuntimeException{
    public UnhandledException(Throwable throwable,String message) {
        super(message,throwable);
    }
}
