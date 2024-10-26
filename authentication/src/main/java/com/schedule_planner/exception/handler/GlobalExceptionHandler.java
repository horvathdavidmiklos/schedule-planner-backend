package com.schedule_planner.exception.handler;

import com.schedule_planner.exception.baseexception.handled.HandledException;
import com.schedule_planner.exception.baseexception.unhandled.UnhandledException;
import com.schedule_planner.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HandledException.class)
    @ResponseBody
    public ResponseEntity<String> handledException(HandledException e) {
        return new ResponseEntity<>(e.getMessage(), e.httpStatus);
    }


    @ExceptionHandler(UnhandledException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> unhandledException(UnhandledException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                e.httpStatus.value(),
                e.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, e.httpStatus);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> otherException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "SOMETHING_WENT_WRONG",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
