package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.services.FileService;
import com.sushchenko.mystictourismapp.services.PlaceService;
import com.sushchenko.mystictourismapp.web.dto.PlaceDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
               .map(place -> modelMapper.map(place, PlaceDTO.class))
               .collect(Collectors.toList());
    }
    @RequestMapping(path = "", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addPlace(@RequestPart PlaceDTO placeDTO,
                                      @RequestPart MultipartFile[] files) throws IOException {
        // Save received files to image directory and set attach property to URIs of the created files
        String path = fileService.createDirectory(placeDTO.getName());
        List<String> fileUrls = fileService.saveFiles(files, path);
        placeDTO.setAttachments(fileUrls.stream() // TODO: maybe need refactoring
                .map(url -> new Attachment(url, new Date()))
                .collect(Collectors.toList()));

        placeService.add(modelMapper.map(placeDTO, Place.class));
        return ResponseEntity.ok("Place successfully added");
    }
}
