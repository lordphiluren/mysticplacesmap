package com.sushchenko.mystictourismapp.entities;

import com.sushchenko.mystictourismapp.entities.enums.Status;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "places")
public class Place {
    @Id
    private String id;
    private String name;
    private User creator;
    private String shortDescription;
    private String fullDescription;
    private String howToGet;
    private String address;
    private double rating;
    private List<Double> rates;
    private Point location;
    private Status status;
    private Set<String> tags;
    private List<Attachment> attachments;
    private List<Comment> comments;
}
