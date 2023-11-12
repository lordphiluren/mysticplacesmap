package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.entities.enums.Status;
import com.sushchenko.mystictourismapp.repos.PlaceRepo;
import com.sushchenko.mystictourismapp.security.AuthenticationFacade;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.utils.exceptions.PlaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
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
        place.setComments(Collections.emptyList());
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
    @Transactional
    public void addComment(String placeId, Comment comment) {
        Place place = getById(placeId);
        comment.setCreator(authFacade.getAuthenticationPrincipal().getUserId());
        comment.setCreatedAt(new Date());
        place.getComments().add(comment);
        placeRepo.save(place);
    }
}
