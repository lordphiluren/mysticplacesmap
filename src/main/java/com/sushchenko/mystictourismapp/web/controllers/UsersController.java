package com.sushchenko.mystictourismapp.web.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.services.UserService;
import com.sushchenko.mystictourismapp.utils.exceptions.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.utils.mappers.UserMapper;
import com.sushchenko.mystictourismapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{username}")
    public UserDTO getUserInfo(@PathVariable String username) {
        return userMapper.mapToUserDTO(userService.getByUsername(username));
    }
    @RequestMapping(path = "/{username}", method = PUT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> updateUserInfo(@PathVariable String username,
                                            @RequestPart UserDTO userDTO,
                                            @RequestPart(required = false) MultipartFile file) {
        User user = userMapper.mapToUser(userDTO);
        user.setId(userService.getByUsername(username).getId());
        userService.addUserProfilePicture(user, file);
        userService.update(user);
        return ResponseEntity.ok("User info successfully updated");
    }

    // Exception Handling

    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        HttpStatus httpStatus = responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
    // validation exception handler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
