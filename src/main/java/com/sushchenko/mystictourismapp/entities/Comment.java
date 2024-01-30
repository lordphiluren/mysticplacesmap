package com.sushchenko.mystictourismapp.entities;

import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private String id;
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private User creator;
    private List<Attachment> attachments;
}
