package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepo extends MongoRepository<Place, String> {
    List<Place> findByTagsIn(List<String> tags);
}
