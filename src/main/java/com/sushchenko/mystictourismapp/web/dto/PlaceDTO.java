package com.sushchenko.mystictourismapp.web.dto;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.entities.Coordinates;
import com.sushchenko.mystictourismapp.entities.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PlaceDTO {
    private String id;
    private String name;
    @Field(targetType = FieldType.OBJECT_ID)
    private String creator;
    private String shortDescription;
    private String fullDescription;
    private String howToGet;
    private String address;
    private Coordinates coordinates;
    private Set<String> tags;
    private List<Attachment> attachments;
    private List<Comment> comments;

}
