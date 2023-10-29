package com.sushchenko.mystictourismapp.entities;

import com.sushchenko.mystictourismapp.entities.enums.Status;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "places")
public class Place {
    @Id
    private String id;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String howToGet;
    private String address;
    private double latitude;
    private double longitude;
    private Status status;

    private Set<Tag> tags;
    private List<Attachment> attachments;
    private List<Comment> comments;
}
