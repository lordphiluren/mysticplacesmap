package com.sushchenko.mystictourismapp.entity;

import com.sushchenko.mystictourismapp.entity.enums.Status;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    @NotNull(message = "Place name can not be empty")
    private String name;
    private User creator;
    @NotNull(message = "Short description can not be empty")
    @Size(max = 100, message = "Short description length should be lower that 100 characters")
    private String shortDescription;
    @Size(max = 1024, message = "Full description length should be lower that 1024 characters")
    private String fullDescription;
    @Size(max = 256, message = "How to get length should be lower than 256 characters")
    private String howToGet;
    @Size(max = 256, message = "Address length should be lower than 256 characters")
    private String address;
    @NotNull(message = "Rating can not be empty")
    private double rating;
    private List<Double> rates;
    @NotNull(message = "Location cannot be empty")
    private Point location;
    private Status status;
    private Set<String> tags;
    private List<Attachment> attachments;
    private List<Comment> comments;
}
