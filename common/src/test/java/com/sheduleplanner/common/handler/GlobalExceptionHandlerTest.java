package com.sheduleplanner.common.handler;

import com.sheduleplanner.common.exception.baseexception.handled.HandledException;
import com.sheduleplanner.common.exception.baseexception.unhandled.UnhandledException;
import com.sheduleplanner.common.exception.handler.GlobalExceptionHandler;
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
    void handleException() {
        var result = globalExceptionHandler.handledException(
                new HandledException("Handled Exception", HttpStatus.INTERNAL_SERVER_ERROR) {
                });
        assertThat(result.getBody()).isEqualTo("Handled Exception");
    }

    @Test
    void unhandledException() {
        var result = globalExceptionHandler.unhandledException(
                new UnhandledException(exception, "UnHandled Exception") {
                }, createWebRequest());
        assertThat(result.getBody().getError()).isEqualTo("UnHandled Exception");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void otherException() {
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
