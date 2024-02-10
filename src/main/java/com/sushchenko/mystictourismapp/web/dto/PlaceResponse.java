package com.sushchenko.mystictourismapp.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sushchenko.mystictourismapp.entity.Attachment;
import com.sushchenko.mystictourismapp.entity.Point;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaceResponse {
    private String id;
    private String name;
    private UserResponse creator;
    private String shortDescription;
    private String fullDescription;
    private String howToGet;
    private String address;
    private double rating;
    private Point location;
    private Set<String> tags;
    private List<Attachment> attachments;
    private List<CommentResponse> comments;
}
