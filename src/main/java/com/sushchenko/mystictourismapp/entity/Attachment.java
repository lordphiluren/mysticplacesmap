package com.sushchenko.mystictourismapp.entity;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private String url;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Attachment(String url) {
        this.url = url;
        this.createdAt = new Date();
    }
}
