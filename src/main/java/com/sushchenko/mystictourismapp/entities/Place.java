package com.sushchenko.mystictourismapp.entities;

import com.sushchenko.mystictourismapp.entities.enums.Status;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "places")
public class Place {
    @Id
    private String id;
    private String name;
    @Field(targetType = FieldType.OBJECT_ID)
    private String creator;
    private String shortDescription;
    private String fullDescription;
    private String howToGet;
    private String address;
    private Location location;
    private Status status;
    private Set<String> tags;
    private List<Attachment> attachments;
    private List<Comment> comments;
}
