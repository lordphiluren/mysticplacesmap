package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.services.CommentService;
import com.sushchenko.mystictourismapp.services.FileService;
import com.sushchenko.mystictourismapp.services.PlaceService;
import com.sushchenko.mystictourismapp.utils.mappers.CommentMapper;
import com.sushchenko.mystictourismapp.utils.mappers.PlaceMapper;
import com.sushchenko.mystictourismapp.web.dto.CommentDTO;
import com.sushchenko.mystictourismapp.web.dto.PlaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlacesController {
    private final CommentMapper commentMapper;
    private final PlaceService placeService;
    private final PlaceMapper placeMapper;
    private final FileService fileService;
    private final CommentService commentService;
    @GetMapping()
    public List<PlaceDTO> getPlaces() {
       return placeService.getAll()
               .stream()
               .map(placeMapper::mapToPlaceDTO)
               .collect(Collectors.toList());
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlaceById(@PathVariable String id) {
        PlaceDTO placeDTO = placeMapper.mapToPlaceDTO(placeService.getById(id));
        List<CommentDTO> comments = commentService.getCommentsByPlaceId(id)
                .stream()
                .map(commentMapper::mapToCommentDTO)
                .toList();
          placeDTO.setComments(comments);
        return ResponseEntity.ok(placeDTO);
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
            place.setAttachments(fileUrls.stream()
                    .map(Attachment::new)
                    .collect(Collectors.toList()));
        }

        placeService.add(placeMapper.mapToPlace(place));
        return ResponseEntity.ok("Place successfully added");
    }
}
