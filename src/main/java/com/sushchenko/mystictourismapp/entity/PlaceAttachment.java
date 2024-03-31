package com.sushchenko.mystictourismapp.entity;

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
@Table(name = "place_attachment")
public class PlaceAttachment {
    @EmbeddedId
    private PlaceAttachmentKey id;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("placeId")
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;
}
