package com.scheduleplanner.common.handler;

import com.scheduleplanner.common.exception.baseexception.handled.HandledException;
import com.scheduleplanner.common.exception.baseexception.unhandled.GatewayTimeoutException;
import com.scheduleplanner.common.exception.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler globalExceptionHandler;
    private Exception exception;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        exception = new Exception("Test Exception");
    }

    @Test
    void test_handleException() {
        var result = globalExceptionHandler.handledException(
                new HandledException("Handled Exception", HttpStatus.INTERNAL_SERVER_ERROR) {
                });
        assertThat(result.getBody()).isEqualTo("Handled Exception");
    }

    @Test
    void test_unhandledException() {
        var result = globalExceptionHandler.unhandledException(
                new GatewayTimeoutException(new RuntimeException()), createWebRequest());
        assertThat(result.getBody().getError()).isEqualTo("GATEWAY_TIMEOUT");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.GATEWAY_TIMEOUT);
    }

    @Test
    void test_otherException() {
        var result = globalExceptionHandler.otherException(exception, createWebRequest());
        assertThat(result.getBody().getError()).isEqualTo("SOMETHING_WENT_WRONG");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private WebRequest createWebRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("http://localhost/test");
        return new ServletWebRequest(request);
    }
}
