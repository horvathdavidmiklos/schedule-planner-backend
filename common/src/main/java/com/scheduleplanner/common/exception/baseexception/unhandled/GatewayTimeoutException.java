package com.scheduleplanner.common.exception.baseexception.unhandled;


import org.springframework.http.HttpStatus;

public class GatewayTimeoutException extends UnhandledException {
    public GatewayTimeoutException(Throwable cause) {
        super(cause,"GATEWAY_TIMEOUT", HttpStatus.GATEWAY_TIMEOUT);
    }
}
