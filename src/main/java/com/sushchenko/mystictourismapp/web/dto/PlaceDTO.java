package com.sushchenko.mystictourismapp.web.dto;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class PlaceDTO {
    private String id;
    private String name;
    private UserDTO creator;
    private String shortDescription;
    private String fullDescription;
    private String howToGet;
    private String address;
    private double rating;
    private GeoJsonPoint location;
    private Set<String> tags;
    private List<Attachment> attachments;
    private List<CommentDTO> comments;
}
