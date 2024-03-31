package com.sushchenko.mystictourismapp.entity;

import com.sushchenko.mystictourismapp.entity.id.PlaceTagKey;
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
@Table(name = "place_tag")
public class PlaceTag {
    @EmbeddedId
    private PlaceTagKey id;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("placeId")
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;
}
