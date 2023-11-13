package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.entities.enums.Status;
import com.sushchenko.mystictourismapp.repos.PlaceRepo;
import com.sushchenko.mystictourismapp.security.AuthenticationFacade;
import com.sushchenko.mystictourismapp.utils.exceptions.PlaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final AuthenticationFacade authFacade;
    private final PlaceRepo placeRepo;

    @Transactional
    public void add(Place place) {
        place.setCreator(authFacade.getAuthenticationPrincipal().getUserId());
        place.setStatus(Status.UNCONFIRMED);
        place.setRates(Collections.emptyList());
        place.setRating(0);
        placeRepo.save(place);
    }
    @Transactional
    public void addRatesById(String id, double rate) {
        Place place = getById(id);
        place.getRates().add(rate);
        place.setRating(countPlaceRating(place));
    }

    @Transactional
    public List<Place> getAll() {
        return placeRepo.findAll();
    }
    @Transactional
    public Place getById(String id) {
        return placeRepo.findById(id)
                .orElseThrow(()-> new PlaceNotFoundException("Place with id: " + id + " doesn't exist"));
    }

    private double countPlaceRating(Place place) {
        List<Double> rates = place.getRates();
        return rates.stream().mapToDouble(Double::doubleValue).sum() / rates.size();
    }

}
