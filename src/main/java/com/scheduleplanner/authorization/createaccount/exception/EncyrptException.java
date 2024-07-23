package com.scheduleplanner.authorization.createaccount.exception;

import com.scheduleplanner.common.exception.baseexception.HandledException;
import com.scheduleplanner.common.exception.baseexception.UnhandledException;

import java.security.NoSuchAlgorithmException;

public class EncyrptException extends UnhandledException {
    public EncyrptException(NoSuchAlgorithmException cause) {
        super(cause,"ENCRYPTION_ERROR");
    }
}
