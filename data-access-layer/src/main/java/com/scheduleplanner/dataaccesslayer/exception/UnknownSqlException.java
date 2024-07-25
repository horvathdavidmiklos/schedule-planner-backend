package com.scheduleplanner.dataaccesslayer.exception;


import com.sheduleplanner.common.exception.baseexception.unhandled.UnhandledException;

public class UnknownSqlException extends UnhandledException {
    public UnknownSqlException(Throwable throwable) {
        super(throwable,"UNKNOWN_SQL_ERROR");
    }
}
