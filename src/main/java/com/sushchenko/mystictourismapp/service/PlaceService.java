package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.*;
import com.sushchenko.mystictourismapp.entity.enums.Status;
import com.sushchenko.mystictourismapp.repo.PlaceRatingRepo;
import com.sushchenko.mystictourismapp.repo.PlaceRepo;
import com.sushchenko.mystictourismapp.repo.PlaceTagRepo;
import com.sushchenko.mystictourismapp.repo.specification.PlaceSpecification;
import com.sushchenko.mystictourismapp.utils.exception.PlaceNotFoundException;
import com.sushchenko.mystictourismapp.utils.exception.PlaceRatingNotFoundException;
import com.sushchenko.mystictourismapp.utils.mapper.PlaceMapper;
import com.sushchenko.mystictourismapp.web.dto.PlaceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepo placeRepo;
    private final PlaceRatingRepo placeRatingRepo;
    private final PlaceTagRepo placeTagRepo;
    private final PlaceMapper placeMapper;
    @Transactional
    public Place add(PlaceRequest placeDto, User user) {
        Place place = placeMapper.toEntity(placeDto);
        enrichPlace(place);
        place.setCreator(user);
        Place savedPlace = placeRepo.save(place);
        addPlaceRating(savedPlace, savedPlace.getCreator(), savedPlace.getRating());
        addTagsToPlace(savedPlace, placeDto.getTags());
        return savedPlace;
    }
    @Transactional
    public List<Place> getAllByFilter(Double ratingStart, Double ratingEnd, String name,
                                      Set<String> tags, Integer offset, Integer limit) {
        int offsetValue = offset != null ? offset : 0;
        int limitValue = limit != null ? limit : Integer.MAX_VALUE;
        Pageable pageable = PageRequest.of(offsetValue, limitValue, Sort.by("id"));
        return placeRepo.findAll(PlaceSpecification.filterPlaces(ratingStart, ratingEnd, name, tags), pageable)
                .getContent();
//        if(tags != null && rating != null) {
//
//        } else if(tags != null) {
//            return placeRepo.findByTagsIn(tags, pageable).getContent();
//        } else if(rating != null) {
//            return placeRepo.findAllByRating(rating, pageable).getContent();
//        } else {
//            return placeRepo.findAll(pageable).getContent();
//        }
    }
    @Transactional
    public Place getById(Long id) {
        return placeRepo.findById(id)
                .orElseThrow(()-> new PlaceNotFoundException("Place with id: " + id + " doesn't exist"));
    }
    @Transactional
    public void deletePlace(Place place) {
        placeRepo.delete(place);
    }
    @Transactional
    public void updatePlace(Place place) {
        placeRepo.save(place);
    }
    @Transactional
    public void addPlaceRating(Place place, User user, Double rating) {
        PlaceRatingKey placeRatingKey = new PlaceRatingKey(place.getId(), user.getId());
        PlaceRating rate = new PlaceRating(placeRatingKey, rating, user, place);
        place.setRating(rating);
        placeRatingRepo.save(rate);
    }
    @Transactional
    public void updatePlaceRating(PlaceRating rating) {
        placeRatingRepo.save(rating);
    }
    @Transactional
    public PlaceRating getPlaceRating(Long userId, Long placeId) {
        PlaceRatingKey id = new PlaceRatingKey(userId, placeId);
        return placeRatingRepo.findById(id)
                .orElseThrow(() ->
                        new PlaceRatingNotFoundException("Rating for place: " +
                        placeId + " by user: " + userId + " doesn't exist"));
    }
    @Transactional
    public void deletePlaceRating(Long userId, Long placeId) {
        PlaceRatingKey id = new PlaceRatingKey(userId, placeId);
        placeRatingRepo.deleteById(id);
    }
    @Transactional
    public void addTagsToPlace(Place place, Set<String> tags) {
        Set<PlaceTag> savedTags = new HashSet<>();
        for(String tag : tags) {
            PlaceTagKey placeTagKey = new PlaceTagKey(place.getId(), tag);
            PlaceTag placeTag = new PlaceTag(placeTagKey, place);
            savedTags.add(placeTag);
        }
        place.setTags(savedTags);
        placeTagRepo.saveAll(savedTags);
    }

    @Transactional
    public void recalculatePlaceRating(Place place) {
        Double rating = placeRatingRepo.findPlaceRatingByPlaceId(place.getId()).stream()
                .mapToDouble(PlaceRating::getRate)
                .average()
                .orElse(0.0);
        place.setRating(rating);
        placeRepo.save(place);
    }
    @Transactional
    public Place recalculatePlaceRating(Long placeId) {
        Place place = placeRepo.findById(placeId).orElseThrow(() -> new PlaceNotFoundException("Place with id: " + placeId + " doesn't exist"));
        Double rating = placeRatingRepo.findPlaceRatingByPlaceId(place.getId()).stream()
                .mapToDouble(PlaceRating::getRate)
                .average()
                .orElse(0.0);
        place.setRating(rating);
        return placeRepo.save(place);
    }
    public boolean checkIfPlaceCreator(User creator, User userPrincipal) {
        return Objects.equals(creator.getId(), userPrincipal.getId());
    }
    private void enrichPlace(Place place) {
        place.setStatus(Status.UNCONFIRMED);
        place.setComments(new HashSet<>());
        place.setCreatedAt(new Date());
        place.setPlaceRates(new HashSet<>());
        place.setTags(new HashSet<>());
    }
}
