package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.services.PlaceService;
import com.sushchenko.mystictourismapp.web.dto.PlaceDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlacesController {
    private final ModelMapper modelMapper;
    private final PlaceService placeService;
    @GetMapping()
    public List<PlaceDTO> getPlaces() {
       return placeService.getAll()
               .stream()
               .map(place -> modelMapper.map(place, PlaceDTO.class))
               .collect(Collectors.toList());
    }
    @PostMapping()
    public ResponseEntity<?> addPlace(@RequestBody PlaceDTO placeDTO) {
        placeService.add(modelMapper.map(placeDTO, Place.class));
        return ResponseEntity.ok("Place successfully added");
    }
}
