package com.sushchenko.mystictourismapp.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String msg) {
        super(msg);
    }
}
