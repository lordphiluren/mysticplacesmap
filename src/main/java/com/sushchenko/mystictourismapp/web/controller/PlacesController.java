package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.entity.Comment;
import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.entity.PlaceRating;
import com.sushchenko.mystictourismapp.entity.PlaceRatingKey;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.service.PlaceService;
import com.sushchenko.mystictourismapp.utils.exception.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.utils.mapper.CommentMapper;
import com.sushchenko.mystictourismapp.utils.mapper.PlaceMapper;
import com.sushchenko.mystictourismapp.utils.validation.UpdateValidation;
import com.sushchenko.mystictourismapp.web.dto.CommentRequest;
import com.sushchenko.mystictourismapp.web.dto.PlaceRequest;
import com.sushchenko.mystictourismapp.web.dto.PlaceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlacesController {
    private final CommentMapper commentMapper;
    private final PlaceService placeService;
    private final PlaceMapper placeMapper;
    @GetMapping()
    public List<PlaceResponse> getPlaces(@RequestParam(name = "tags", required = false) Set<String> tags,
                                         @RequestParam(name = "rating", required = false) Double rating,
                                         @RequestParam(name = "offset", required = false) Integer offset,
                                         @RequestParam(name = "limit", required = false) Integer limit) {
        return placeService.getAllByFilter(tags, rating, offset, limit).stream()
                .map(placeMapper::toDto)
                .collect(Collectors.toList());
    }
    @PostMapping()
    public ResponseEntity<?> addPlace(@Valid @RequestBody PlaceRequest placeDto,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Place place = placeMapper.toEntity(placeDto);
        place.setCreator(userPrincipal.getUser());
        Place savedPlace = placeService.add(place);
        placeService.addPlaceRating(savedPlace, savedPlace.getCreator(), savedPlace.getRating());
        placeService.addTagsToPlace(savedPlace, placeDto.getTags());
        return ResponseEntity.ok(placeMapper.toDto(savedPlace));
    }
    @GetMapping("/{id}")
    public PlaceResponse getPlaceById(@PathVariable Long id) {
        return placeMapper.toDto(placeService.getById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable Long id,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Place place = placeService.getById(id);
        if(placeService.checkIfPlaceCreator(place.getCreator(), userPrincipal.getUser())) {
            placeService.deletePlace(place);
            return ResponseEntity.ok("Place successfully deleted");
        } else {
            return new ResponseEntity<>("User is not allowed to modify this place", HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlace(@PathVariable Long id,
                                         @Validated(UpdateValidation.class) @RequestBody PlaceRequest placeDto,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Place place = placeService.getById(id);
        Double rating = place.getRating();
        placeMapper.mergeDtoIntoEntity(placeDto, place);
        if(placeService.checkIfPlaceCreator(place.getCreator(), userPrincipal.getUser())) {
            place.setRating(rating);
            placeService.updatePlace(place);
            return ResponseEntity.ok("Place successfully updated");
        } else {
            return new ResponseEntity<>("User is not allowed to modify this place", HttpStatus.FORBIDDEN);
        }
    }
    // TODO
    // добавить нормальное сообщение об ошибках в случае ошибок уникальности первичного ключа
    @PostMapping("/{id}/rates")
    public ResponseEntity<?> addRating(@PathVariable Long id,
                                       @Validated(UpdateValidation.class) @RequestBody PlaceRequest placeDto,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Place place = placeService.getById(id);
        placeService.addPlaceRating(place, userPrincipal.getUser(), placeDto.getRating());
        placeService.recalculatePlaceRating(place);
        return ResponseEntity.ok(placeMapper.toDto(place));
    }
    @PutMapping("/{id}/rates")
    public ResponseEntity<?> updateRating(@PathVariable Long id,
                                          @Validated(UpdateValidation.class) @RequestBody PlaceRequest placeDto,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        PlaceRating rating = placeService.getPlaceRating(userPrincipal.getUser().getId(), id);
        rating.setRate(placeDto.getRating());
        placeService.updatePlaceRating(rating);
        return ResponseEntity.ok(placeMapper.toDto(placeService.recalculatePlaceRating(id)));
    }
    @DeleteMapping("/{id}/rates")
    public ResponseEntity<?> deleteRating(@PathVariable Long id,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        placeService.deletePlaceRating(userPrincipal.getUser().getId(), id);
        placeService.recalculatePlaceRating(id);
        return ResponseEntity.ok("Rating successfully deleted");
    }
    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addCommentToPlace(@PathVariable Long id,
                                               @Valid @RequestBody CommentRequest commentDTO,
                                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Place place = placeService.getById(id);
        Set<Comment> comments = place.getComments();

        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setCreator(userPrincipal.getUser());

        comments.add(comment);
        place.setComments(comments);
        placeService.updatePlace(place);
        return ResponseEntity.ok("Comment successfully added");
    }
}
