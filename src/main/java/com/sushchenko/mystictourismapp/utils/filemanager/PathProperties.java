package com.sushchenko.mystictourismapp.utils.filemanager;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PathProperties {
    @Value("${application.places.attachments.path}")
    private String placeAttachmentsPath;
    @Value("${application.comments.attachments.path}")
    private String commentAttachmentsPath;
    @Value("${application.user.attachments.path}")
    private String userAttachmentsPath;
}
