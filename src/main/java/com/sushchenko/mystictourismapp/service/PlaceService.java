package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.*;
import com.sushchenko.mystictourismapp.entity.enums.Status;
import com.sushchenko.mystictourismapp.entity.id.PlaceAttachmentKey;
import com.sushchenko.mystictourismapp.entity.id.PlaceRatingKey;
import com.sushchenko.mystictourismapp.entity.id.PlaceTagKey;
import com.sushchenko.mystictourismapp.repo.PlaceAttachmentRepo;
import com.sushchenko.mystictourismapp.repo.PlaceRatingRepo;
import com.sushchenko.mystictourismapp.repo.PlaceRepo;
import com.sushchenko.mystictourismapp.repo.PlaceTagRepo;
import com.sushchenko.mystictourismapp.repo.specification.PlaceSpecification;
import com.sushchenko.mystictourismapp.service.helper.Helper;
import com.sushchenko.mystictourismapp.utils.exception.NotEnoughPermissionsException;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepo placeRepo;
    private final PlaceRatingRepo placeRatingRepo;
    private final PlaceTagRepo placeTagRepo;
    private final PlaceMapper placeMapper;
    private final UploadService uploadService;
    private final PlaceAttachmentRepo placeAttachRepo;
    @Transactional
    public Place add(PlaceRequest placeDto, MultipartFile[] attachments, User user) {
        Place place = placeMapper.toEntity(placeDto);
        enrichPlace(place);
        place.setCreator(user);
        Place savedPlace = placeRepo.save(place);
        if(attachments != null) {
            addAttachmentsToPlace(savedPlace, attachments);
        }
        addPlaceRating(savedPlace, savedPlace.getCreator(), savedPlace.getRating());
        addTagsToPlace(savedPlace, placeDto.getTags());
        return savedPlace;
    }
    @Transactional
    public List<Place> getAllByFilter(Double ratingStart, Double ratingEnd, String name,
                                      Set<String> tags, Integer offset, Integer limit, Long userId) {
        int offsetValue = offset != null ? offset : 0;
        int limitValue = limit != null ? limit : Integer.MAX_VALUE;
        Pageable pageable = PageRequest.of(offsetValue, limitValue, Sort.by("id"));
        return placeRepo.findAll(PlaceSpecification.filterPlaces(ratingStart, ratingEnd, name, tags, userId), pageable)
                .getContent();
    }
    @Transactional
    public Place getById(Long id) {
        return placeRepo.findById(id)
                .orElseThrow(()-> new PlaceNotFoundException("Place with id: " + id + " doesn't exist"));
    }
    @Transactional
    public void deletePlace(Long id, User creator) {
        Place place = getById(id);
        if(Helper.checkUserPermissions(place.getCreator(), creator)) {
            placeRepo.deleteById(id);
        } else {
            throw new NotEnoughPermissionsException("User with id:" + creator.getId() +
                    " is not allowed to modify this place");
        }
    }
    @Transactional
    public Place updatePlace(Long id, PlaceRequest placeDto, User creator) {
        Place place = getById(id);
        Double rating = place.getRating();
        placeMapper.mergeDtoIntoEntity(placeDto, place);
        if(Helper.checkUserPermissions(place.getCreator(), creator)) {
            place.setRating(rating);
            place = placeRepo.save(place);
        } else {
            throw new NotEnoughPermissionsException("User with id:" + creator.getId() +
                    " is not allowed to modify this place");
        }
        return place;
    }
    @Transactional
    public Place addRates(Long id, User user, Double rating) {
        Place place = getById(id);
        addPlaceRating(place, user, rating);
        return place;
    }
    @Transactional
    public void addPlaceRating(Place place, User user, Double rating) {
        PlaceRatingKey placeRatingKey = new PlaceRatingKey(user.getId(), place.getId());
        PlaceRating rate = new PlaceRating(placeRatingKey, rating, user, place);
        place.setRating(rating);
        placeRatingRepo.save(rate);
    }
    @Transactional
    public Place updatePlaceRating(Long placeId, User creator, Double rate) {
        PlaceRatingKey id = new PlaceRatingKey(creator.getId(), placeId);
        PlaceRating rating = getPlaceRating(id);
        rating.setRate(rate);
        PlaceRating placeRating = placeRatingRepo.save(rating);
        return recalculatePlaceRating(placeRating.getPlace());
    }
    @Transactional
    public PlaceRating getPlaceRating(PlaceRatingKey id) {
        return placeRatingRepo.findById(id)
                .orElseThrow(() ->
                        new PlaceRatingNotFoundException("Rating for place: " +
                        id.getPlaceId() + " by user: " + id.getUserId() + " doesn't exist"));
    }
    @Transactional
    public PlaceRating deletePlaceRating(Long placeId, User creator) {
        PlaceRatingKey id = new PlaceRatingKey(creator.getId(), placeId);
        PlaceRating rating = getPlaceRating(id);
        placeRatingRepo.deleteById(id);
        return rating;
    }
    @Transactional
    public void addTagsToPlace(Place place, Set<String> tags) {
        Set<PlaceTag> tagsToSave = new HashSet<>();
        for(String tag : tags) {
            PlaceTagKey placeTagKey = new PlaceTagKey(place.getId(), tag);
            PlaceTag placeTag = new PlaceTag(placeTagKey, place);
            tagsToSave.add(placeTag);
        }
        List<PlaceTag> savedTags = placeTagRepo.saveAll(tagsToSave);
        Set<PlaceTag> placeTags = place.getTags();
        placeTags.addAll(savedTags);
        place.setTags(placeTags);
    }

    @Transactional
    public void addAttachmentsToPlace(Place place, MultipartFile[] attachments) {
        Set<String> urls = uploadService.uploadAttachments(Arrays.asList(attachments));
        Set<PlaceAttachment> attachmentsToSave = new HashSet<>();
        for(String url : urls) {
            PlaceAttachmentKey placeAttachmentKey = new PlaceAttachmentKey(place.getId(), url);
            PlaceAttachment placeAttachment = new PlaceAttachment(placeAttachmentKey, place);
            attachmentsToSave.add(placeAttachment);
        }
        List<PlaceAttachment> savedAttachments = placeAttachRepo.saveAll(attachmentsToSave);
        Set<PlaceAttachment> placeAttachments = place.getAttachments();
        placeAttachments.addAll(savedAttachments);
        place.setAttachments(placeAttachments);
    }
    @Transactional
    public Place recalculatePlaceRating(Place place) {
        Double rating = place.getPlaceRates().stream()
                .mapToDouble(PlaceRating::getRate)
                .average()
                .orElse(0.0);
        place.setRating(rating);
        return placeRepo.save(place);
    }
    @Transactional
    public Place addPlaceAttachments(Long id, MultipartFile[] attachments) {
        Place place = getById(id);
        addAttachmentsToPlace(place, attachments);
        return place;
    }
    private void enrichPlace(Place place) {
        place.setStatus(Status.UNCONFIRMED);
        place.setComments(new HashSet<>());
        place.setCreatedAt(new Date());
        place.setPlaceRates(new HashSet<>());
        place.setTags(new HashSet<>());
        place.setAttachments(new HashSet<>());
    }
}
