//package com.sushchenko.mystictourismapp.web.controllers;
//
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.sushchenko.mystictourismapp.services.CommentService;
//import com.sushchenko.mystictourismapp.utils.exceptions.ControllerErrorResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/comments")
//@RequiredArgsConstructor
//public class CommentsController {
//    private final CommentService commentService;
//
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<?> deleteComment(@PathVariable String commentId) {
//        commentService.deleteComment(commentService.getById(commentId));
//        return ResponseEntity.ok("Comment Successfully deleted");
//    }
//
//
//    // Exception Handling
//    @ExceptionHandler
//    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
//        ControllerErrorResponse errorResponse = new ControllerErrorResponse(e.getMessage(),
//                System.currentTimeMillis());
//        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
//        HttpStatus httpStatus = responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
//        return new ResponseEntity<>(errorResponse, httpStatus);
//    }
//    // validation exception handler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(
//            MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
//}
