package com.sushchenko.mystictourismapp.entity;

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
@Table(name = "place_rating")
public class PlaceRating {
    @EmbeddedId
    private PlaceRatingKey id;
    @Column(name = "rate")
    private Double rate;

    // Relations
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("placeId")
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;
}
