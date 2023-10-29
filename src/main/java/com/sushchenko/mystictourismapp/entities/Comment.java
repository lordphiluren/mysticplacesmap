package com.sushchenko.mystictourismapp.entities;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

public class Comment {
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private User creator;
}
