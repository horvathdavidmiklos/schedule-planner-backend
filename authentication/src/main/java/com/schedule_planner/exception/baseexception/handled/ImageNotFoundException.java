package com.schedule_planner.exception.baseexception.handled;

import org.springframework.http.HttpStatus;

public class ImageNotFoundException extends HandledException {
    public ImageNotFoundException() {
        super("IMAGE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
