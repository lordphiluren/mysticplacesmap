package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.entity.Comment;
import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.service.PlaceService;
import com.sushchenko.mystictourismapp.utils.exception.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.utils.mapper.CommentMapper;
import com.sushchenko.mystictourismapp.utils.mapper.PlaceMapper;
import com.sushchenko.mystictourismapp.utils.mapper.UserMapper;
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
    private final UserMapper userMapper;
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
        placeService.addPlaceRating(savedPlace);
        placeService.addTagsToPlace(savedPlace, placeDto.getTags());
        return ResponseEntity.ok(placeMapper.toDto(savedPlace));
    }
    @GetMapping("/{id}")
    public PlaceResponse getPlaceById(@PathVariable Long id) {
        return placeMapper.toDto(placeService.getById(id));
    }
//    @PostMapping("/{id}")
//    public ResponseEntity<?> addRating(@PathVariable String id, @Valid @RequestBody PlaceRequest placeDto) {
//        placeService.addRatesById(id, placeDto.getRating());
//        return ResponseEntity.ok("Rating added");
//    }
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
        placeMapper.mergeDtoIntoEntity(placeDto, place);
        if(placeService.checkIfPlaceCreator(place.getCreator(), userPrincipal.getUser())) {
            placeService.updatePlace(place);
            return ResponseEntity.ok("Place successfully updated");
        } else {
            return new ResponseEntity<>("User is not allowed to modify this place", HttpStatus.FORBIDDEN);
        }
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

    // Exception Handling
    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        HttpStatus httpStatus = responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
    // Validation exception handler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
