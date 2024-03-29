package com.sushchenko.mystictourismapp.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PlaceRatingKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "place_id")
    private Long placeId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceRatingKey that = (PlaceRatingKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, placeId);
    }
}
