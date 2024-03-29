package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.service.PlaceService;
import com.sushchenko.mystictourismapp.utils.mapper.PlaceMapper;
import com.sushchenko.mystictourismapp.utils.validation.UpdateValidation;
import com.sushchenko.mystictourismapp.web.dto.PlaceRequest;
import com.sushchenko.mystictourismapp.web.dto.PlaceResponse;
import com.sushchenko.mystictourismapp.web.dto.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@Tag(name = "Places", description = "Places and places' rates manipulations")
public class PlacesController {
    private final PlaceService placeService;
    private final PlaceMapper placeMapper;
    @Operation(
            summary = "Get places",
            description = "Get list of places with filter and pagination sorted by id"
    )
    @GetMapping()
    public List<PlaceResponse> getPlaces(@RequestParam(name = "ratingStart", required = false) Double ratingStart,
                                         @RequestParam(name = "ratingEnd", required = false) Double ratingEnd,
                                         @RequestParam(name = "name", required = false) String name,
                                         @RequestParam(name = "tags", required = false) Set<String> tags,
                                         @RequestParam(name = "offset", required = false) Integer offset,
                                         @RequestParam(name = "limit", required = false) Integer limit,
                                         @RequestParam(name = "user", required = false) Long userId) {
        return placeService.getAllByFilter(ratingStart, ratingEnd, name, tags, offset, limit, userId).stream()
                .map(placeMapper::toDto)
                .collect(Collectors.toList());
    }
    @Operation(
            summary = "Add place"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping()
    public ResponseEntity<?> addPlace(@Valid @RequestBody PlaceRequest placeDto,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(placeMapper.toDto(placeService.add(placeDto, userPrincipal.getUser())));
    }
    @Operation(
            summary = "Get place by id"
    )
    @GetMapping("/{id}")
    public PlaceResponse getPlaceById(@PathVariable Long id) {
        return placeMapper.toDto(placeService.getById(id));
    }
    @Operation(
            summary = "Delete place by id"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable Long id,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        placeService.deletePlace(id, userPrincipal.getUser());
        return ResponseEntity.ok(new ResponseMessage("Place successfully deleted"));
    }
    @Operation(
            summary = "Update place"
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlace(@PathVariable Long id,
                                         @Validated(UpdateValidation.class) @RequestBody PlaceRequest placeDto,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(placeMapper.toDto(placeService.updatePlace(id, placeDto, userPrincipal.getUser())));
    }
    @Operation(
            summary = "Add rates to place"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/{id}/rates")
    public ResponseEntity<?> addRating(@PathVariable Long id,
                                       @Validated(UpdateValidation.class) @RequestBody PlaceRequest placeDto,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Place place = placeService.addRates(id, userPrincipal.getUser(), placeDto.getRating());
        return ResponseEntity.ok(placeMapper.toDto(
                placeService.recalculatePlaceRating(place))
        );
    }
    @Operation(
            summary = "Update user's rate"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}/rates")
    public ResponseEntity<?> updateRating(@PathVariable Long id,
                                          @Validated(UpdateValidation.class) @RequestBody PlaceRequest placeDto,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok(placeMapper.toDto(
                placeService.updatePlaceRating(id, userPrincipal.getUser(), placeDto.getRating()))
        );
    }
    @Operation(
            summary = "Delete user's rate"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}/rates")
    public ResponseEntity<?> deleteRating(@PathVariable Long id,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        placeService.recalculatePlaceRating(
                placeService.deletePlaceRating(id, userPrincipal.getUser()).getPlace()
        );
        return ResponseEntity.ok(new ResponseMessage("Rating successfully deleted"));
    }
//    @RequestMapping(path = "/{id}/attachments", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity<?> addPlaceAttachments(@PathVariable Long id, @RequestPart List<MultipartFile> attachments) {
//        return ResponseEntity.ok(placeMapper.toDto(placeService.addPlaceAttachments(id, attachments)));
//    }
}
