package com.sushchenko.mystictourismapp.entity;

import com.sushchenko.mystictourismapp.entity.id.CommentAttachmentKey;
import com.sushchenko.mystictourismapp.entity.id.PlaceAttachmentKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_attachment")
public class CommentAttachment {
    @EmbeddedId
    private CommentAttachmentKey id;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("commentId")
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;
}
