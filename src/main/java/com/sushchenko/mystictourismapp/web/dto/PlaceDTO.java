package com.sushchenko.mystictourismapp.web.dto;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.entities.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class PlaceDTO {
    private String id;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String howToGet;
    private String address;
    private double latitude;
    private double longitude;
    private Set<String> tags;
    private List<Attachment> attachments;
    private List<Comment> comments;
}
