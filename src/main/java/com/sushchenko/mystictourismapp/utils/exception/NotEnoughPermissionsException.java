package com.sushchenko.mystictourismapp.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotEnoughPermissionsException extends RuntimeException {
    public NotEnoughPermissionsException(String msg) {
        super(msg);
    }
}
