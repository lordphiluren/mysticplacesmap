package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.services.CommentService;
import com.sushchenko.mystictourismapp.services.PlaceService;
import com.sushchenko.mystictourismapp.utils.exceptions.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.utils.filemanager.FileManager;
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
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlacesController {
    private final CommentMapper commentMapper;
    private final PlaceService placeService;
    private final PlaceMapper placeMapper;
    private final CommentService commentService;
    @GetMapping()
    public List<PlaceDTO> getPlaces() {
       return placeService.getAll()
               .stream()
               .map(placeMapper::mapToPlaceDTO)
               .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public PlaceDTO getPlaceById(@PathVariable String id) {
        PlaceDTO placeDTO = placeMapper.mapToPlaceDTO(placeService.getById(id));
        List<CommentDTO> comments = commentService.getCommentsByPlaceId(id)
                .stream()
                .map(commentMapper::mapToCommentDTO)
                .toList();
          placeDTO.setComments(comments);
        return placeDTO;
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> addRating(@PathVariable String id, @RequestBody PlaceDTO placeDTO) {
        placeService.addRatesById(id, placeDTO.getRating());
        return ResponseEntity.ok("Rating added");
    }
    @RequestMapping(path = "", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addPlace(@RequestPart PlaceDTO placeDTO,
                                      @RequestPart MultipartFile[] files) {
        Place place = placeMapper.mapToPlace(placeDTO);
        placeService.add(placeService.addPlaceAttachments(place, files));
        return ResponseEntity.ok("Place successfully added");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable String id) {
        placeService.deletePlace(placeService.getById(id));
        return ResponseEntity.ok("Place successfully deleted");
    }
    @RequestMapping(path = "/{id}/comments", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addCommentToPlace(@PathVariable String id,
                                               @RequestPart CommentDTO commentDTO,
                                               @RequestPart MultipartFile[] files) {
        Comment comment = commentMapper.mapToComment(commentDTO);
        comment.setPlaceId(id);
        commentService.addComment(commentService.addCommentAttachments(comment, files));
        return ResponseEntity.ok("Comment successfully added");
    }
    @RequestMapping(path = "/{id}", method = PUT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> updatePlace(@PathVariable String id,
                                         @RequestPart PlaceDTO placeDTO,
                                         @RequestPart MultipartFile[] files) {
        Place place = placeMapper.mapToPlace(placeDTO);
        placeService.deletePlaceAttachments(placeService.getById(id));
        place.setId(id);
        place.setCreator(placeDTO.getCreator().getId());
        placeService.updatePlace(placeService.addPlaceAttachments(place, files));
        return ResponseEntity.ok("Place successfully updated");
    }
    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
