package com.sheduleplanner.common.exception.baseexception.unhandled;

public abstract class UnhandledException extends RuntimeException{
    public UnhandledException(Throwable throwable,String message) {
        super(message,throwable);
    }
}
