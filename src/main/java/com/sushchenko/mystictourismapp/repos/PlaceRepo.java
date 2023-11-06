package com.sushchenko.mystictourismapp.repos;

import com.sushchenko.mystictourismapp.entities.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepo extends MongoRepository<Place, String> {
}
