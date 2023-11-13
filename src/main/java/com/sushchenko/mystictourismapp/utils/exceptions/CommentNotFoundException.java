package com.sushchenko.mystictourismapp.utils.exceptions;

import com.sushchenko.mystictourismapp.entities.Comment;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String msg) {
        super(msg);
    }
}
