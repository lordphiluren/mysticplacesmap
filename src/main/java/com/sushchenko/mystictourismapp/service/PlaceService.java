package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.*;
import com.sushchenko.mystictourismapp.entity.enums.Status;
import com.sushchenko.mystictourismapp.repo.PlaceRatingRepo;
import com.sushchenko.mystictourismapp.repo.PlaceRepo;
import com.sushchenko.mystictourismapp.repo.PlaceTagRepo;
import com.sushchenko.mystictourismapp.utils.exception.PlaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
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
        place.setComments(new HashSet<>());
        place.setCreatedAt(new Date());
        place.setPlaceRates(new HashSet<>());
        place.setTags(new HashSet<>());
        place = placeRepo.save(place);
        return place;
    }
    @Transactional
    public List<Place> getAll() {
        return placeRepo.findAll(Sort.by("id"));
    }
    @Transactional
    public List<Place> getAllByFilter(Set<String> tags, Double rating, Integer offset, Integer limit) {
        int offsetValue = offset != null ? offset : 0;
        int limitValue = limit != null ? limit : Integer.MAX_VALUE;
        Pageable pageable = PageRequest.of(offsetValue, limitValue, Sort.by("id"));
        if(tags != null && rating != null) {
            return placeRepo.findByTagsAndRating(tags, rating, pageable).getContent();
        } else if(tags != null) {
            return placeRepo.findByTagsIn(tags, pageable).getContent();
        } else if(rating != null) {
            return placeRepo.findAllByRating(rating, pageable).getContent();
        } else {
            return placeRepo.findAll(pageable).getContent();
        }
    }
    @Transactional
    public Place getById(Long id) {
        return placeRepo.findById(id)
                .orElseThrow(()-> new PlaceNotFoundException("Place with id: " + id + " doesn't exist"));
    }
    private double countPlaceRating(Place place) {
        return placeRatingRepo.findPlaceRatingByPlaceId(place.getId()).stream()
                .mapToDouble(PlaceRating::getRate)
                .average()
                .orElse(0.0);
    }
    public void addPlaceRating(Place place) {
        PlaceRatingKey placeRatingKey = new PlaceRatingKey(place.getId(), place.getCreator().getId());
        PlaceRating rate = new PlaceRating(placeRatingKey, place.getRating(), place.getCreator(), place);
        placeRatingRepo.save(rate);
    }
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
