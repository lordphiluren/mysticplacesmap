package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.entities.enums.Status;
import com.sushchenko.mystictourismapp.repos.PlaceRepo;
import com.sushchenko.mystictourismapp.security.AuthenticationFacade;
import com.sushchenko.mystictourismapp.utils.exceptions.PlaceNotFoundException;
import com.sushchenko.mystictourismapp.utils.filemanager.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final AuthenticationFacade authFacade;
    private final PlaceRepo placeRepo;
    private final FileManager fileManager;

    @Transactional
    public void add(Place place) {
        place.setCreator(authFacade.getAuthenticationPrincipal().getUserId());
        place.setStatus(Status.UNCONFIRMED);
        place.setRates(Collections.emptyList());
        place.setRating(0);
        placeRepo.save(place);
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
    @Transactional
    public void addRatesById(String id, double rate) {
        Place place = getById(id);
        List<Double> rates = place.getRates();
        rates.add(rate);
        place.setRates(rates);
        place.setRating(countPlaceRating(place));
        placeRepo.save(place);
    }
    public Place addPlaceAttachments(Place place, MultipartFile[] files) {
        // Save received files to image directory and set attach property to URLs of the created files
        if(files.length != 0) {
            String path = fileManager.createPlacesDirectory(place.getName());
            List<String> fileUrls = fileManager.saveFiles(files, path);
            place.setAttachments(fileUrls.stream()
                    .map(Attachment::new)
                    .collect(Collectors.toList()));
        }
        return place;
    }

}
