package com.sushchenko.mystictourismapp.entities;

import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Field(targetType = FieldType.OBJECT_ID)
    private String creator;
    @Field(targetType = FieldType.OBJECT_ID)
    private String placeId;
    private List<Attachment> attachments;

}
