package com.scheduleplanner.dataaccesslayer.exception;

import com.scheduleplanner.common.exception.baseexception.UnhandledException;

public class UnknownSqlException extends UnhandledException {
    public UnknownSqlException(Throwable throwable) {
        super(throwable,"UNKNOWN_SQL_ERROR");
    }
}
