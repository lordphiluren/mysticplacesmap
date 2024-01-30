package com.sushchenko.mystictourismapp.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Point;
import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaceDTO {
    private String id;
    private String name;
    private UserDTO creator;
    private String shortDescription;
    private String fullDescription;
    private String howToGet;
    private String address;
    private double rating;
    private Point location;
    private Set<String> tags;
    private List<Attachment> attachments;
    private List<CommentDTO> comments;
}
