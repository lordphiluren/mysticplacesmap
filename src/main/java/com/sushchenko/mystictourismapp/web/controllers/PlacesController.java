package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.services.FileService;
import com.sushchenko.mystictourismapp.services.PlaceService;
import com.sushchenko.mystictourismapp.web.dto.PlaceDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlacesController {
    private final ModelMapper modelMapper;
    private final PlaceService placeService;
    private final FileService fileService;
    @GetMapping()
    public List<PlaceDTO> getPlaces() {
       return placeService.getAll()
               .stream()
               .map(this::mapToPlaceDto)
               .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public PlaceDTO getPlaceById(@PathVariable String id) {
        return mapToPlaceDto(placeService.getById(id));
    }

    @GetMapping()
    // TODO: убрать создание файла, если было выкинуто исключение от уникального индекса в бд
    @RequestMapping(path = "", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addPlace(@RequestPart PlaceDTO place,
                                      @RequestPart MultipartFile[] files) throws IOException {
        // Save received files to image directory and set attach property to URLs of the created files
        if(files.length != 0) {
            String path = fileService.createDirectory(place.getName());
            List<String> fileUrls = fileService.saveFiles(files, path);
            // TODO: maybe need refactoring
            place.setAttachments(fileUrls.stream()
                    .map(Attachment::new)
                    .collect(Collectors.toList()));
        }

        placeService.add(mapToPlace(place));
        return ResponseEntity.ok("Place successfully added");
    }

    // Mappers
    private PlaceDTO mapToPlaceDto(Place place) {
        return modelMapper.map(place, PlaceDTO.class);
    }
    private Place mapToPlace(PlaceDTO placeDTO) {
        return modelMapper.map(placeDTO, Place.class);
    }
}
