package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.services.CommentService;
import com.sushchenko.mystictourismapp.utils.exceptions.ControllerErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentService.getById(commentId));
        return ResponseEntity.ok("Comment Successfully deleted");
    }
    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
