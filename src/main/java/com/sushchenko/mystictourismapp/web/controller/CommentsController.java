package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.entity.Comment;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.service.CommentService;
import com.sushchenko.mystictourismapp.utils.mapper.CommentMapper;
import com.sushchenko.mystictourismapp.web.dto.CommentRequest;
import com.sushchenko.mystictourismapp.web.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/places/{placeId}/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    @GetMapping()
    public List<CommentResponse> getPlaceComments(@PathVariable Long placeId,
                                                  @RequestParam(name = "offset", required = false) Integer offset,
                                                  @RequestParam(name = "limit", required = false) Integer limit) {
        return commentService.getCommentsByPlaceId(placeId, offset, limit).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
    @PostMapping()
    public ResponseEntity<?> addComment(@PathVariable Long placeId,
                                        @RequestBody CommentRequest commentDto,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Comment comment = commentMapper.toEntity(commentDto);
        return ResponseEntity.ok(
            commentMapper.toDto(commentService.addComment(placeId, comment, userPrincipal.getUser()))
        );
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @RequestBody CommentRequest commentDto,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(
            commentMapper.toDto(commentService.updateComment(commentId, commentDto, userPrincipal.getUser()))
        );
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        commentService.deleteComment(commentId, userPrincipal.getUser());
        return ResponseEntity.ok("Comment successfully deleted");
    }
}
