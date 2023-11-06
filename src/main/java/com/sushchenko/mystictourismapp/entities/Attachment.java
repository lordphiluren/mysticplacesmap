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
public class Attachment {
    private String url;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
