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
public class PlaceAttachmentKey implements Serializable {
    @Column(name = "place_id")
    private Long placeId;
    @Column(name = "url")
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceAttachmentKey that = (PlaceAttachmentKey) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, placeId);
    }
}
