package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.entity.PlaceRating;
import com.sushchenko.mystictourismapp.entity.PlaceRatingKey;
import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.entity.enums.Status;
import com.sushchenko.mystictourismapp.repo.PlaceRatingRepo;
import com.sushchenko.mystictourismapp.repo.PlaceRepo;
import com.sushchenko.mystictourismapp.utils.exception.PlaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepo placeRepo;
    private final PlaceRatingRepo placeRatingRepo;

    @Transactional
    public void add(Place place) {
        place.setStatus(Status.UNCONFIRMED);
        place.setComments(Collections.emptyList());
        place.setCreatedAt(new Date());
        if(place.getRating() == null) {
            place.setRating(0.0);
            placeRepo.save(place);
        } else {
            Place savedPlace = placeRepo.save(place);
            addPlaceRating(savedPlace.getRating(), savedPlace.getCreator(), savedPlace);
        }
    }
    // TODO
    // Убрать у тегов айди, сделать ключом имя и сделать сущность Place_Tag с айди места имя тега
    // TODO
    // Add sorting by name, rating, tags
    // Add pagination
    @Transactional
    public List<Place> getAll() {
        return placeRepo.findAll();
    }
    @Transactional
    public List<Place> getAllByTags(List<String> tags) {
        return placeRepo.findByTagsIn(tags);
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
    private void addPlaceRating(Double rating, User creator, Place place) {
        PlaceRating rate = new PlaceRating(rating, creator, place);
        PlaceRatingKey placeRatingKey = new PlaceRatingKey(place.getId(), creator.getId());
        rate.setId(placeRatingKey);
        placeRatingRepo.save(rate);
    }
//    public void addPlaceAttachments(Place place, MultipartFile[] files) {
//        if(files!=null) {
//        // Save received files to image directory and set attach property to URLs of the created files
//            if(files.length != 0) {
//                String path = fileManager.createDirectory(place.getName());
//                List<String> fileUrls = fileManager.saveFiles(files, path);
//                place.setAttachments(fileUrls.stream()
//                        .map(Attachment::new)
//                        .collect(Collectors.toList()));
//            }
//        }
//    }
    @Transactional
    public void deletePlace(Place place) {
        placeRepo.delete(place);
    }
    @Transactional
    public void updatePlace(Place place) {
        placeRepo.save(place);
    }
}
