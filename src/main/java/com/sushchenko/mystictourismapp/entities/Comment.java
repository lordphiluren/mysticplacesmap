package com.sushchenko.mystictourismapp.entities;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private User creator;
}
