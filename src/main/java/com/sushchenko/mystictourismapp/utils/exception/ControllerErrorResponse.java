package com.sushchenko.mystictourismapp.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ControllerErrorResponse {
    private String message;
    private long timestamp;
}
