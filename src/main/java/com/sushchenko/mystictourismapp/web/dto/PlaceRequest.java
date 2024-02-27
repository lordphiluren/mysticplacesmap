package com.sushchenko.mystictourismapp.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sushchenko.mystictourismapp.entity.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaceRequest {
    @NotNull(message = "Place name can not be empty")
    private String name;
    private UserRequest creator;
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
    private Double rating;
    @NotNull(message = "Latitude cannot be empty")
    private Double latitude;
    @NotNull(message = "Longitude cannot be empty")
    private Double longitude;
    private Set<Tag> tags;
}
