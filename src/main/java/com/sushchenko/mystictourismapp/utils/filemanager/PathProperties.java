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
    @Value("${application.attachments.places.path}")
    private String placeAttachmentsPath;
    @Value("${application.attachments.comments.path}")
    private String commentAttachmentsPath;
    @Value("${application.attachments.user.path}")
    private String userAttachmentsPath;
}
