package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.services.CommentService;
import com.sushchenko.mystictourismapp.services.PlaceService;
import com.sushchenko.mystictourismapp.utils.exceptions.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.utils.filemanager.FileManager;
import com.sushchenko.mystictourismapp.utils.mappers.CommentMapper;
import com.sushchenko.mystictourismapp.web.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/places/{id}/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    @RequestMapping(path = "", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addCommentToPlace(@PathVariable String id,
                                               @RequestPart CommentDTO commentDTO,
                                               @RequestPart MultipartFile[] files) {
        Comment comment = commentMapper.mapToComment(commentDTO);
        comment.setPlaceId(id);
        commentService.addComment(commentService.addCommentAttachments(comment, files));
        return ResponseEntity.ok("Comment successfully added");
    }

    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
