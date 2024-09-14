package com.scheduleplanner.common.exception.handler;

import com.scheduleplanner.common.exception.baseexception.handled.HandledException;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class BaseController {
    protected ResponseEntity<String> handledException(Runnable runnable, String response) {
        try {
            runnable.run();
            return ResponseEntity.ok().body(response);
        }catch (HandledException handledException){
            return new ResponseEntity<>(handledException.getMessage(), handledException.httpStatus);
        }
    }

    protected <T> ResponseEntity<?> handledException(Supplier<T> supplier) {
        try {
            return ResponseEntity.ok().body(supplier.get());
        }catch (HandledException handledException){
            return new ResponseEntity<>(handledException.getMessage(), handledException.httpStatus);
        }
    }
}
