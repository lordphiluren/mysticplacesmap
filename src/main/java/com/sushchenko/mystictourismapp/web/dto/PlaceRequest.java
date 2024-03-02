package com.sushchenko.mystictourismapp.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sushchenko.mystictourismapp.utils.validation.UpdateValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Validated({UpdateValidation.class})
public class PlaceRequest {
    @NotNull(message = "Place name can not be empty")
    private String name;
    private UserRequest creator;
    @NotNull(message = "Short description can not be empty")
    @Size(max = 100, message = "Short description length should be lower that 100 characters", groups = {UpdateValidation.class})
    private String shortDescription;
    @Size(max = 1024, message = "Full description length should be lower that 1024 characters", groups = {UpdateValidation.class})
    private String fullDescription;
    @Size(max = 256, message = "How to get length should be lower than 256 characters", groups = {UpdateValidation.class})
    private String howToGet;
    @Size(max = 256, message = "Address length should be lower than 256 characters", groups = {UpdateValidation.class})
    private String address;
    @NotNull(message = "Rating can not be empty")
    @Max(value = 5, message = "Rating can't be more than 5")
    @Min(value = 0, message = "Rating can't be less than 0")
    private Double rating;
    @NotNull(message = "Latitude cannot be empty")
    private Double latitude;
    @NotNull(message = "Longitude cannot be empty")
    private Double longitude;
    private Set<String> tags;
}
