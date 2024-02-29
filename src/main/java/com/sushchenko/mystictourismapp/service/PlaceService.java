package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.*;
import com.sushchenko.mystictourismapp.entity.enums.Status;
import com.sushchenko.mystictourismapp.repo.PlaceRatingRepo;
import com.sushchenko.mystictourismapp.repo.PlaceRepo;
import com.sushchenko.mystictourismapp.repo.PlaceTagRepo;
import com.sushchenko.mystictourismapp.utils.exception.PlaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepo placeRepo;
    private final PlaceRatingRepo placeRatingRepo;
    private final PlaceTagRepo placeTagRepo;

    @Transactional
    public Place add(Place place) {
        place.setStatus(Status.UNCONFIRMED);
        place.setComments(Collections.emptySet());
        place.setCreatedAt(new Date());
        place = placeRepo.save(place);
        return place;
    }
    @Transactional
    public List<Place> getAll(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return placeRepo.findAll(pageable).getContent();
    }
    @Transactional
    public List<Place> getAll() {
        return placeRepo.findAll();
    }

    @Transactional
    public List<Place> getAllByTags(Set<String> tags) {
        return placeRepo.findByTagsIn(tags);
    }
    @Transactional
    public Place getById(Long id) {
        return placeRepo.findById(id)
                .orElseThrow(()-> new PlaceNotFoundException("Place with id: " + id + " doesn't exist"));
    }
    private double countPlaceRating(Place place) {
        // Мб юзать геттер плейс рейтс
        return placeRatingRepo.findPlaceRatingByPlaceId(place.getId()).stream()
                .mapToDouble(PlaceRating::getRate)
                .average()
                .orElse(0.0);
    }
    @Transactional
    public void addPlaceRating(Place place) {
        PlaceRatingKey placeRatingKey = new PlaceRatingKey(place.getId(), place.getCreator().getId());
        PlaceRating rate = new PlaceRating(placeRatingKey, place.getRating(), place.getCreator(), place);
        placeRatingRepo.save(rate);
    }
    @Transactional
    public void addTagsToPlace(Place place, Set<String> tags) {
        Set<PlaceTag> savedTags = new HashSet<>();
        for(String tag : tags) {
            PlaceTagKey placeTagKey = new PlaceTagKey(place.getId(), tag);
            PlaceTag placeTag = new PlaceTag(placeTagKey, place);
            savedTags.add(placeTag);
        }
        placeTagRepo.saveAll(savedTags);
    }
    @Transactional
    public void deletePlace(Place place) {
        placeRepo.delete(place);
    }
    @Transactional
    public void updatePlace(Place place) {
        placeRepo.save(place);
    }
}
