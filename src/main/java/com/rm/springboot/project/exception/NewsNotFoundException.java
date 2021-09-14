package com.rm.springboot.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "news not found ")
public class NewsNotFoundException extends RuntimeException {

    public NewsNotFoundException(String message) {
        super(message);
    }

    public NewsNotFoundException() {
    }
}
