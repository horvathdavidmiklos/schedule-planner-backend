package com.sheduleplanner.common.exception.baseexception.unhandled;


public class UnknownSqlException extends UnhandledException {
    public UnknownSqlException(Throwable throwable) {
        super(throwable,"UNKNOWN_SQL_ERROR");
    }
}
