package com.sushchenko.mystictourismapp.utils.exceptions;

import com.sushchenko.mystictourismapp.web.dto.PlaceDTO;

public class PlaceNotFoundException extends RuntimeException{
    public PlaceNotFoundException(String msg) {
        super(msg);
    }
}
