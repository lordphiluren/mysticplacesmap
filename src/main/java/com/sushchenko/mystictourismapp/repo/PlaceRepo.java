package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {
    List<Place> findByTagsIn(List<String> tags);
}
