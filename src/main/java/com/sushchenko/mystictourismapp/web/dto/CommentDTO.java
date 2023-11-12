package com.sushchenko.mystictourismapp.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@NoArgsConstructor
public class CommentDTO {
    private String text;
    @Field(targetType = FieldType.OBJECT_ID)
    private String creator;
}
