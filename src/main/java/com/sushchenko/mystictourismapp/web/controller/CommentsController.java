package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.entity.Comment;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.service.CommentService;
import com.sushchenko.mystictourismapp.utils.mapper.CommentMapper;
import com.sushchenko.mystictourismapp.web.dto.CommentRequest;
import com.sushchenko.mystictourismapp.web.dto.CommentResponse;
import com.sushchenko.mystictourismapp.web.dto.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/places/{placeId}/comments")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Comments manipulations")
public class CommentsController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    @Operation(
            summary = "Get comments",
            description = "Get list of comments with pagination sorted by creation date"
    )
    @GetMapping()
    public List<CommentResponse> getPlaceComments(@PathVariable Long placeId,
                                                  @RequestParam(name = "offset", required = false) Integer offset,
                                                  @RequestParam(name = "limit", required = false) Integer limit) {
        return commentService.getCommentsByPlaceId(placeId, offset, limit).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
    @Operation(
            summary = "Add comment"
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(@PathVariable Long placeId,
                                        @RequestPart("comment") CommentRequest commentDto,
                                        @RequestPart(value = "attachments", required = false) MultipartFile[] attachments,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Comment comment = commentMapper.toEntity(commentDto);
        return ResponseEntity.ok(
            commentMapper.toDto(commentService.addComment(placeId, comment, attachments, userPrincipal.getUser()))
        );
    }
    @Operation(
            summary = "Update comment by id"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @RequestBody CommentRequest commentDto,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(
            commentMapper.toDto(commentService.updateComment(commentId, commentDto, userPrincipal.getUser()))
        );
    }
    @Operation(
            summary = "Delete comment by id"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        commentService.deleteComment(commentId, userPrincipal.getUser());
        return ResponseEntity.ok(new ResponseMessage("Comment successfully deleted"));
    }
}
