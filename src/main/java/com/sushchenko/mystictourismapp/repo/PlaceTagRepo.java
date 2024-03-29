package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.PlaceTag;
import com.sushchenko.mystictourismapp.entity.id.PlaceTagKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceTagRepo extends JpaRepository<PlaceTag, PlaceTagKey> {

}
