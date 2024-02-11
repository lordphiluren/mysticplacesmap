package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.entity.enums.Status;
import com.sushchenko.mystictourismapp.repo.PlaceRepo;
import com.sushchenko.mystictourismapp.utils.exception.PlaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepo placeRepo;

    @Transactional
    public void add(Place place) {
        place.setStatus(Status.UNCONFIRMED);
        place.setRates(Collections.singletonList(place.getRating()));
        place.setComments(Collections.emptyList());
        placeRepo.save(place);
    }
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
    public Place getById(String id) {
        return placeRepo.findById(id)
                .orElseThrow(()-> new PlaceNotFoundException("Place with id: " + id + " doesn't exist"));
    }

    private double countPlaceRating(Place place) {
        List<Double> rates = place.getRates();
        return rates.stream().mapToDouble(Double::doubleValue).sum() / rates.size();
    }
    @Transactional
    public void addRatesById(String id, double rate) {
        Place place = getById(id);
        List<Double> rates = place.getRates();
        rates.add(rate);
        place.setRates(rates);
        place.setRating(countPlaceRating(place));
        placeRepo.save(place);
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
        //fileManager.deletePlaceAttachments(place);
        //commentService.deleteCommentsByPlaceId(place.getId());
        placeRepo.delete(place);
    }
    @Transactional
    public void updatePlace(Place place) {
        placeRepo.save(place);
    }
}
