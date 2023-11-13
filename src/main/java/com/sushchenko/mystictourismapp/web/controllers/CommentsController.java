package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.services.CommentService;
import com.sushchenko.mystictourismapp.services.PlaceService;
import com.sushchenko.mystictourismapp.utils.mappers.CommentMapper;
import com.sushchenko.mystictourismapp.web.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/places/{id}/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    @PostMapping("")
    public ResponseEntity<?> addCommentToPlace(@PathVariable String id, @RequestBody CommentDTO commentDto) {
        commentService.addComment(id, commentMapper.mapToComment(commentDto));
        return ResponseEntity.ok("Comment successfully added");
    }
}
