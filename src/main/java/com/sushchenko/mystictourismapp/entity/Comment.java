package com.sushchenko.mystictourismapp.entity;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @NotNull(message = "Comment text can not be empty")
    @Size(max = 1024, message = "Comment length should not be greater than 1024 characters")
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private User creator;
    private List<Attachment> attachments;
}
