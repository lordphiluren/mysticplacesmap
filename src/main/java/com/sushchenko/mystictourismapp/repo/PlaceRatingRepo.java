package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.PlaceRating;
import com.sushchenko.mystictourismapp.entity.PlaceRatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRatingRepo extends JpaRepository<PlaceRating, PlaceRatingKey> {
    List<PlaceRating> findPlaceRatingByPlaceId(Long placeId);
    Optional<PlaceRating> findById(PlaceRatingKey placeRatingKey);
}
