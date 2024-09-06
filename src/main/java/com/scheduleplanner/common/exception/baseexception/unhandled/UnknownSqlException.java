package com.scheduleplanner.common.exception.baseexception.unhandled;


import org.springframework.http.HttpStatus;

public class UnknownSqlException extends UnhandledException {
    public UnknownSqlException(Throwable throwable) {
        super(throwable,"UNKNOWN_SQL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
