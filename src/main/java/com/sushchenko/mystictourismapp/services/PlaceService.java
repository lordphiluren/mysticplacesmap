package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.entities.enums.Status;
import com.sushchenko.mystictourismapp.repos.PlaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepo placeRepo;

    @Transactional
    public void add(Place place) {
        place.setStatus(Status.UNCONFIRMED);
        placeRepo.save(place);
    }

    @Transactional
    public List<Place> getAll() {
        return placeRepo.findAll();
    }
}
