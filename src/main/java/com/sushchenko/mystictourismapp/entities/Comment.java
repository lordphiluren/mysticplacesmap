package com.sushchenko.mystictourismapp.entities;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Field(targetType = FieldType.OBJECT_ID)
    private String creator;

    public Comment(String text, String creator) {
        this.text = text;
        this.creator = creator;
        this.createdAt = new Date();
    }
}
