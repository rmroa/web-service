package com.rm.springboot.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "comments not found ")
public class CommentsNotFoundException extends RuntimeException{

    public CommentsNotFoundException(String message) {
        super(message);
    }

    public CommentsNotFoundException() {
    }
}
